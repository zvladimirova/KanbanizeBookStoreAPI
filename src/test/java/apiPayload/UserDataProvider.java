package apiPayload;

import com.github.javafaker.Faker;

public class UserDataProvider {
    public User userWithValidData(){
        Faker faker = new Faker();

        User userPayload= new User();
        userPayload.setUserName(faker.name().username());
        userPayload.setPassword("qqA3224343!");

        return userPayload;
    }


    public User userWithEmptyUsername(){

        User userPayload= new User();
        userPayload.setUserName("");
        userPayload.setPassword("qqA3224343!");

        return userPayload;
    }

    public User userWithEmptyPassword(){
        Faker faker = new Faker();

        User userPayload= new User();
        userPayload.setUserName(faker.name().username());
        userPayload.setPassword("");

        return userPayload;
    }

    public User userWithWithShortPassword(){
        Faker faker = new Faker();

        User userPayload= new User();
        userPayload.setUserName(faker.name().username());
        userPayload.setPassword("qA323!");

        return userPayload;
    }
    public User userWithPasswordWithoutUpperCase(){
        Faker faker = new Faker();

        User userPayload= new User();
        userPayload.setUserName(faker.name().username());
        userPayload.setPassword("qq3224333");

        return userPayload;
    }
    public User userWithPasswordWithoutLowerCase(){
        Faker faker = new Faker();

        User userPayload= new User();
        userPayload.setUserName(faker.name().username());
        userPayload.setPassword("A32!3");

        return userPayload;
    }
    public User userWithPasswordWithoutSpecialChar(){
        Faker faker = new Faker();

        User userPayload= new User();
        userPayload.setUserName(faker.name().username());
        userPayload.setPassword("qqA3224233");

        return userPayload;
    }

    public User userWithPasswordWithoutNumber(){
        Faker faker = new Faker();

        User userPayload= new User();
        userPayload.setUserName(faker.name().username());
        userPayload.setPassword("qqAaafdfsfs!");

        return userPayload;
    }
}
