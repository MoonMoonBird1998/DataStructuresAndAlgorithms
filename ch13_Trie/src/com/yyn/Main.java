package com.yyn;

import com.sun.xml.internal.ws.addressing.WsaActionUtil;

public class Main {

    public static void main(String[] args) {
        Trie<Integer> trie = new Trie<>();
        trie.add("d",1);
        trie.add("do",2);
        trie.add("dog",3);
        trie.add("dogs",4);
        trie.add("obvious",20);
        trie.add("小猪佩奇", 50);
        trie.add("小猪", 550);
        System.out.println(trie.size());
        System.out.println(trie.get("小猪"));
        System.out.println(trie.get("小猪佩奇"));
        System.out.println(trie.contains("obvious"));
        System.out.println(trie.startsWith("猪"));
        System.out.println(trie.startsWith("o"));
        System.out.println(trie.startsWith("obviou"));
        System.out.println(trie.startsWith("小猪"));
        System.out.println(trie.remove("猪"));
        System.out.println(trie.remove("dogs"));
        System.out.println(trie.size());
/*        trie.clear();
        System.out.println(trie.size());
        System.out.println(trie.startsWith("小猪"));*/

    }
}
