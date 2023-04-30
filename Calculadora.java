
package com.mycompany.eddtorresdehanoi;
import java.util.Scanner;
import java.util.Stack;
public class Calculadora {

    private static Object notacao;
    public static void main(String[] args) {
        
        Scanner input = new Scanner(System.in);
        
        System.out.println("Digite a expressão matemática:");
        String expressao = input.nextLine();
        
        System.out.println("Digite a notação da expressão (infixa, prefixa ou posfixa):");
        String notacao = input.nextLine();
        
        String resultado = calcular(expressao, notacao);
        System.out.println("Resultado: " + resultado);
        
        System.out.println("Expressão na notação infixa: " + converterParaInfixa(expressao, notacao));
        System.out.println("Expressão na notação prefixa: " + converterParaPrefixa(expressao));
        System.out.println("Expressão na notação posfixa: " + converterParaPosfixa(expressao, notacao));
        
        input.close();
    }
private static String calcular(String expressao, String notacao) {
    switch (notacao) {
        case "posfixa":
            return converterParaPosfixa(expressao);
        case "infixa":
            return converterParaInfixa(expressao);
        case "prefixa":
            return converterParaPrefixa(expressao);
        default:
            throw new IllegalArgumentException("Notação inválida.");
    }
}
private static String converterParaPosfixa(String expressao, String notacao) {
    Stack<String> pilha = new Stack<>();
    String[] elementos = expressao.split(" ");
    StringBuilder posfixa = new StringBuilder();

    for (String elemento : elementos) {
        if (isOperador(elemento)) {
            while (!pilha.isEmpty() && prioridade(pilha.peek()) >= prioridade(elemento)) {
                posfixa.append(pilha.pop()).append(" ");
            }
            pilha.push(elemento);
        } else if (elemento.equals("(")) {
            pilha.push(elemento);
        } else if (elemento.equals(")")) {
            while (!pilha.peek().equals("(")) {
                posfixa.append(pilha.pop()).append(" ");
            }
            pilha.pop();
        } else {
            posfixa.append(elemento).append(" ");
        }
    }

    while (!pilha.isEmpty()) {
        posfixa.append(pilha.pop()).append(" ");
    }

    return posfixa.toString().trim();
}
private static String converterParaInfixa(String expressao, String notacao) {
    Stack<String> pilha = new Stack<>();
    String[] elementos = expressao.split(" ");
    
    for (String elemento : elementos) {
        if (isOperando(elemento)) {
            pilha.push(elemento);
        } else {
            String operando2 = pilha.pop();
            String operando1 = pilha.pop();
            String subExpressao = "(" + operando1 + " " + elemento + " " + operando2 + ")";
            pilha.push(subExpressao);
        }
    }
    
    return pilha.pop();
}
private static String converterParaPrefixa(String expressao) {
    if (notacao.equals("prefixa")) {
        return expressao;
    }
    
    String[] elementos = new StringBuilder(expressao).reverse().toString().split(" ");
    Stack<String> pilha = new Stack<>();
    StringBuilder prefixa = new StringBuilder();
    
    for (String elemento : elementos) {
        if (isOperador(elemento)) {
            while (!pilha.isEmpty() && prioridade(pilha.peek()) > prioridade(elemento)) {
                prefixa.append(pilha.pop()).append(" ");
            }
            pilha.push(elemento);
        } else {
            prefixa.append(elemento).append(" ");
        }
    }
    
    while (!pilha.isEmpty()) {
        prefixa.append(pilha.pop()).append(" ");
    }
    
    return prefixa.reverse().toString().trim();
}

private static String converterInfixaParaPrefixa(String infixa) {

    String prefixa = "";
    Stack<String> pilha = new Stack<String>();
    String[] elementos = infixa.split(" ");

    for (int i = elementos.length - 1; i >= 0; i--) {

        if (isOperador(elementos[i])) {

            while (!pilha.isEmpty() && prioridade(pilha.peek()) >= prioridade(elementos[i])) {

                prefixa += pilha.pop() + " ";
            }

            pilha.push(elementos[i]);

        } else if (elementos[i].equals(")")) {

            pilha.push(elementos[i]);

        } else if (elementos[i].equals("(")) {

            while (!pilha.peek().equals(")")) {

                prefixa += pilha.pop() + " ";
            }

            pilha.pop();

        } else {

            prefixa += elementos[i] + " ";
        }
    }

    while (!pilha.isEmpty()) {

        prefixa += pilha.pop() + " ";
    }

    return new StringBuilder(prefixa).reverse().toString().trim();
}
private static String converterPrefixaParaInfixa(String prefixa) {
    Stack<String> pilha = new Stack<>();
    String[] elementos = new StringBuilder(prefixa).reverse().toString().split(" ");
    
    for (int i = 0; i < elementos.length; i++) {
        if (isOperador(elementos[i])) {
            String a = pilha.pop();
            String b = pilha.pop();
            pilha.push("(" + a + " " + elementos[i] + " " + b + ")");
        } else {
            pilha.push(elementos[i]);
        }
    }
    
    return pilha.pop();
}
private static String converterInfixaParaPosfixa(String infixa) {
    StringBuilder posfixa = new StringBuilder();
    Stack<String> pilha = new Stack<>();
    String[] elementos = infixa.split(" ");

    for (int i = 0; i < elementos.length; i++) {
        if (isOperador(elementos[i])) {
            while (!pilha.isEmpty() && !pilha.peek().equals("(") && prioridade(pilha.peek()) >= prioridade(elementos[i])) {
                posfixa.append(pilha.pop()).append(" ");
            }
            pilha.push(elementos[i]);
        } else if (elementos[i].equals("(")) {
            pilha.push(elementos[i]);
        } else if (elementos[i].equals(")")) {
            while (!pilha.peek().equals("(")) {
                posfixa.append(pilha.pop()).append(" ");
            }
            pilha.pop();
        } else {
            posfixa.append(elementos[i]).append(" ");
        }
    }

    while (!pilha.isEmpty()) {
        posfixa.append(pilha.pop()).append(" ");
    }

    return posfixa.toString().trim();
}
private static boolean isOperador(String elemento) {
    return elemento.equals("+") || elemento.equals("-") || elemento.equals("*") || elemento.equals("/");
}
private static int prioridade(String operador) {
    switch (operador) {
        case "+":
        case "-":
            return 1;
        case "*":
        case "/":
            return 2;
        case "^":
            return 3;
        default:
            return 0;
    }
}

    private static double calcularPosfixa(String posfixa) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    private static boolean isOperando(String elemento) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    private static String converterParaInfixa(String expressao) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    private static String converterParaPosfixa(String expressao) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}


