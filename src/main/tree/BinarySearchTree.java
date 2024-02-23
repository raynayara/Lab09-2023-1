package tree;

import java.util.ArrayList;
import java.util.List;

import estrut.Tree;

public class BinarySearchTree implements Tree {
    private static class Node {
        int valor, altura;
        Node left, right;

        Node(int item) {
            valor = item;
            altura = 1;
        }
    }

    private Node root;

    @Override
    public boolean buscaElemento(int valor) {
        return buscaElementoRec(root, valor);
    }

    private boolean buscaElementoRec(Node node, int valor) {
        if (node == null) return false;
        if (valor < node.valor) return buscaElementoRec(node.left, valor);
        if (valor > node.valor) return buscaElementoRec(node.right, valor);
        return true;
    }

    private int altura(Node node) {
        return node == null ? 0 : node.altura;
    }

    private int calculaNovaAltura(Node node) {
        return Math.max(altura(node.left), altura(node.right)) + 1;
    }

    private int balanceamento(Node node) {
        return node == null ? 0 : altura(node.left) - altura(node.right);
    }

    private Node rotacaoDireita(Node y) {
        Node x = y.left;
        Node T2 = x.right;

        x.right = y;
        y.left = T2;

        y.altura = calculaNovaAltura(y);
        x.altura = calculaNovaAltura(x);

        return x;
    }

    private Node rotacaoEsquerda(Node x) {
        Node y = x.right;
        Node T2 = y.left;

        y.left = x;
        x.right = T2;

        x.altura = calculaNovaAltura(x);
        y.altura = calculaNovaAltura(y);

        return y;
    }

    @Override
    public void insereElemento(int valor) {
        root = insereElementoRec(root, valor);
    }

    private Node insereElementoRec(Node node, int valor) {
        if (node == null) return new Node(valor);

        if (valor < node.valor) node.left = insereElementoRec(node.left, valor);
        else if (valor > node.valor) node.right = insereElementoRec(node.right, valor);
        else return node;

        node.altura = calculaNovaAltura(node);

        int balance = balanceamento(node);

        if (balance > 1 && valor < node.left.valor) return rotacaoDireita(node);
        if (balance < -1 && valor > node.right.valor) return rotacaoEsquerda(node);
        if (balance > 1 && valor > node.left.valor) {
            node.left = rotacaoEsquerda(node.left);
            return rotacaoDireita(node);
        }
        if (balance < -1 && valor < node.right.valor) {
            node.right = rotacaoDireita(node.right);
            return rotacaoEsquerda(node);
        }

        return node;
    }

    @Override
    public void remove(int valor) {
        root = removeRec(root, valor);
    }

    private Node removeRec(Node node, int valor) {
        if (node == null) return node;

        if (valor < node.valor) node.left = removeRec(node.left, valor);
        else if (valor > node.valor) node.right = removeRec(node.right, valor);
        else {
            if (node.left == null || node.right == null) {
                Node temp = (node.left == null) ? node.right : node.left;
                if (temp == null) {
                    temp = node;
                    node = null;
                } else node = temp;
            } else {
                Node temp = minimoNode(node.right);
                node.valor = temp.valor;
                node.right = removeRec(node.right, temp.valor);
            }
        }

        if (node == null) return node;

        node.altura = calculaNovaAltura(node);

        int balance = balanceamento(node);

        if (balance > 1 && balanceamento(node.left) >= 0) return rotacaoDireita(node);
        if (balance > 1 && balanceamento(node.left) < 0) {
            node.left = rotacaoEsquerda(node.left);
            return rotacaoDireita(node);
        }
        if (balance < -1 && balanceamento(node.right) <= 0) return rotacaoEsquerda(node);
        if (balance < -1 && balanceamento(node.right) > 0) {
            node.right = rotacaoDireita(node.right);
            return rotacaoEsquerda(node);
        }

        return node;
    }

    private Node minimoNode(Node node) {
        Node current = node;
        while (current.left != null) current = current.left;
        return current;
    }

    @Override
    public int[] preOrdem() {
        List<Integer> result = new ArrayList<>();
        preOrdemRec(root, result);
        return result.stream().mapToInt(Integer::intValue).toArray();
    }

    private void preOrdemRec(Node node, List<Integer> result) {
        if (node != null) {
            result.add(node.valor);
            preOrdemRec(node.left, result);
            preOrdemRec(node.right, result);
        }
    }

    @Override
    public int[] emOrdem() {
        List<Integer> result = new ArrayList<>();
        emOrdemRec(root, result);
        return result.stream().mapToInt(Integer::intValue).toArray();
    }

    private void emOrdemRec(Node node, List<Integer> result) {
        if (node != null) {
            emOrdemRec(node.left, result);
            result.add(node.valor);
            emOrdemRec(node.right, result);
        }
    }

    @Override
    public int[] posOrdem() {
        List<Integer> result = new ArrayList<>();
        posOrdemRec(root, result);
        return result.stream().mapToInt(Integer::intValue).toArray();
    }

    private void posOrdemRec(Node node, List<Integer> result) {
        if (node != null) {
            posOrdemRec(node.left, result);
            posOrdemRec(node.right, result);
            result.add(node.valor);
        }
    }

    @Override
    public int minimo() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'minimo'");
    }

    @Override
    public int maximo() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'maximo'");
    }
}
