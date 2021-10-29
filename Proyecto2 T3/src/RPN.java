import java.util.ArrayList;
import java.util.Stack;

public class RPN {
    private ArrayList<String> prefija;
    private Stack<String> pila;

    public RPN(ArrayList<String> prefija){
        this.prefija = prefija;
        this.pila = new Stack();
    }

    public double rpn() throws ElementoFaltante {
        /**
         * Vuelve a notación pos-fija y calcula el resultado
         * @autor Isa Córdoba
         */
        String eleDer, eleIzq;

        for (String token : prefija){
            if (esOperador(token)){
                eleDer=pila.pop();
                if (pila.isEmpty()){
                    throw new ElementoFaltante("Error, elemento faltante");
                }
                eleIzq = pila.pop();
                double resultado= operar(eleIzq,token,eleDer);
                pila.push(""+resultado);
            } else{
                pila.push(token);
            }
        }
        return Double.parseDouble(pila.pop());
    }

    private boolean esOperador(String token){
        return token.equals("+") || token.equals("-") || token.equals("*") || token.equals("/") || token.equals("%");
    }

    private double operar(String eleIzq, String operador, String eleDer) {
        double a = Double.parseDouble(eleIzq);
        double b = Double.parseDouble(eleDer);

        switch (operador){
            case "+" : return a+b;
            case "-" : return a-b;
            case "*" : return a*b;
            case "/" : return a/b;
            case "%" : return a%b;
            default: return -1;
        }
    }
}
