package com.yyn;

public class Test {
    public static void main(String[] args) {

        ArrayList<User> users = new ArrayList<>();
        users.add(new User("lisi", 23));
        users.add(new User("lisi1", 23));
        users.add(new User("lisi2", 23));
        users.add(new User("lisi3", 23));
        System.out.println(users);
        users.add(2, null);
        System.out.println(users);

        ArrayList<Integer> list = new ArrayList<>();
        for (int i = 0; i < 30; i++){
            list.add(i);
        }
        System.out.println(list);


    }
}
