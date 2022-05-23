package cn.edu.gzy.model;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 将微博程序中与文件I/O 有关的操作，全部集中到UserService中进行统一管理。
 */
public class UserService {
    private final String USERS;
    public UserService(String USERS){
        this.USERS = USERS;
    }
    /**
     * 如果输入正确，则创建用户，尚未讲到数据库，先把数据保存到文件中
     * @param phone
     * @param password
     */
    public void tryCreateUser(String phone, String password) throws IOException {
        Path userHome = Paths.get(USERS, phone);
        if(Files.notExists(userHome)){//检查用户文件夹是否创建，以确认用户是否已经注册
            createUser(userHome, phone,password);
        }
    }
    /**
     * 创建用户
     * @param userHome
     * @param phone
     * @param password
     */
    public void createUser(Path userHome, String phone, String password) throws IOException{
        Files.createDirectories(userHome);
        int salt = (int)(Math.random() * 100);//随机生成0-100之间的整数
        String encrypt = String.valueOf(salt + password.hashCode());//密码做了简单的加密处理。
        Path profile = userHome.resolve("profile.txt");
        System.out.println("保存账号密码的文件路径为:"+profile.toAbsolutePath());
        try(BufferedWriter bufferedWriter = Files.newBufferedWriter(profile)){
            bufferedWriter.write(String.format("%s\t%s\t%d",phone,encrypt,salt));
        }
    }

    /**
     * 登录函数，判断输入的账号密码是否匹配
     * @param loginName
     * @param password
     * @return
     * @throws IOException
     */
    public boolean login(String loginName, String password) throws IOException{
        if(loginName != null && loginName.trim().length() != 0 && password != null){
            Path userHome = Paths.get(USERS,loginName);
            return Files.exists(userHome) && isCorrectPassword(password, userHome);
        }
        return  false;
    }

    /**
     * 判断密码是否正确
     * @param password
     * @param userHome
     * @return
     * @throws IOException
     */
    public boolean isCorrectPassword(String password, Path userHome) throws IOException{
        Path profile = userHome.resolve("profile.txt");
        try(BufferedReader bufferedReader = Files.newBufferedReader(profile)){
            String[] data = bufferedReader.readLine().split("\t");
            System.out.println("后台获取的密码长度："+data.length);
            int encrypt = Integer.parseInt(data[1]);
            int salt = Integer.parseInt(data[2]);
            return password.hashCode() + salt == encrypt;
        }
    }

    /**
     * 获取登录用户的微博信息，注意微博信息是保存在文件中的
     * @param username
     * @return
     */
    /*public Map<Long, String> messages(String username) throws IOException{
        //1. 构造保存路径
        Path userHome = Paths.get(USERS, username);
        //构造保存微博信息的Map对象，为了排序，使用了TreeMap
        //Comparator.reverseOrder是Java 8中引入的一个静态方法，它返回比较器，对对象集合进行反向自然排序。
        Map<Long, String> messages = new TreeMap<>(Comparator.reverseOrder());
        //显示指定目录中的所有.txt文件
        try(DirectoryStream<Path> logs = Files.newDirectoryStream(userHome, "*.log")){
            for(Path log : logs){
                String millis = log.getFileName().toString().replace(".log", "");//获得文件名，这里保存的时候，文件名用的是自1970年1月1日0时0分以来的毫秒数
                //从文件中读取字节流，并用系统分隔符进行连接
                String msg = Files.readAllLines(log).stream()
                        .collect(Collectors.joining(System.lineSeparator()));
                messages.put(Long.parseLong(millis),msg);
            }
        }
        return messages;
    }*/
    public List<Message> messages(String username) throws IOException{
        //1. 构造保存路径
        Path userHome = Paths.get(USERS, username);
        //构造保存微博信息的Map对象，为了排序，使用了TreeMap
        //Comparator.reverseOrder是Java 8中引入的一个静态方法，它返回比较器，对对象集合进行反向自然排序。
        List<Message> messages = new ArrayList<>();
        //显示指定目录中的所有.txt文件
        try(DirectoryStream<Path> logs = Files.newDirectoryStream(userHome, "*.log")){
            for(Path log : logs){
                String millis = log.getFileName().toString().replace(".log", "");//获得文件名，这里保存的时候，文件名用的是自1970年1月1日0时0分以来的毫秒数
                //从文件中读取字节流，并用系统分隔符进行连接
                String msg = Files.readAllLines(log).stream()
                        .collect(Collectors.joining(System.lineSeparator()));
                messages.add(new Message(username, Long.parseLong(millis), msg));
            }
        }
        return messages;
    }


    /**
     * 将新发表的符合要求的微博，添加到程序中
     * @param username
     * @param msg
     */
    public void addMessage(String username, String msg) throws IOException{
        //1. 构造保存有微博信息的文件路径
        Path txt = Paths.get(USERS, username, String.format("%s.log", Instant.now().toEpochMilli()));
        try (BufferedWriter writer = Files.newBufferedWriter(txt)) {//将微博数据保存到登录账户的文件夹中，且保存数据的文件名称用时间戳来表示
            writer.write(msg);
        }
    }
    /**
     * 删除微博，也就是将指定的保存微博的文件删除。
     * @param username
     * @param millis
     * @throws IOException
     */
    public void deleteMessage(String username, String millis) throws IOException {
        //1. 构造保存路径
        Path txt = Paths.get(USERS, username, String.format("%s.log",millis));
        Files.delete(txt);
    }

}
