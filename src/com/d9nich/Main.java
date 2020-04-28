package com.d9nich;

import com.d9nich.AVL.AVLTree;

import java.util.Random;

public class Main {

    public static void main(String[] args) {
        AVLTree<Field> tree = generator();
        Random random = new Random();
        for (int i = 0; i < 15; i++) {
            tree.searchAndReturn(new Field(random.nextInt(10_000), ""));
            System.out.println((i + 1) + ")comparision - " + AVLTree.getCounter());
        }

    }

    private static AVLTree<Field> generator() {
        AVLTree<Field> structure = new AVLTree<>();
        Random random = new Random();
        for (int i = 0; i < 10_000; i++) {
            StringBuilder key = new StringBuilder();
            for (int j = 0; j < random.nextInt(20); j++) {
                int choice = random.nextInt(('9' - '0' + 1) + ('Z' - 'A' + 1));
                key.append(((char) (choice < 10 ? '0' + choice : choice - 10 + 'A')));
            }
            structure.insert(new Field(key.toString()));
        }
        return structure;
    }
}
