import java.util.ArrayList;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ShuntingYard {
    private String operacion;
    private ArrayList<String> prefija;
    private ArrayList<String> tokens;

    public ShuntingYard(String operacion) throws ParentesisAperturaException, ParentesisCierreException {
        this.operacion = operacion;
        prefija = new ArrayList<>();
        this.tokens = new ArrayList<>();
        this.tokenizar();
        this.shuntingYard();
    }

    private void tokenizar() {
        String patron = "(?<token>[\\(]|\\d+|[-+\\*/%]|[\\)])";
        Pattern pattern = Pattern.compile(patron);
        Matcher matcher = pattern.matcher(this.operacion);
        String token;
        while (matcher.find()) {
            token = matcher.group("token");
            tokens.add(token);
        }
    }

    public void shuntingYard() throws ParentesisCierreException, ParentesisAperturaException {
        String token;
        int contador = 0;
        Stack<String> pila = new Stack();
        for (int i = 0; i < tokens.size(); i++) {
            token = tokens.get(i);

            if (token.matches("\\d+")) {
                prefija.add(token);
            } else if (token.equals("(")) {
                pila.push(token);
                contador++;
            } else if (token.matches("[-+\\*/%]")) {
                if(!pila.empty()){
                    if(compararPresedencias(token,pila.peek())){
                        prefija.add(pila.pop());
                        pila.push(token);
                    }
                    else {
                        pila.push(token);
                    }
                }
                else {
                    pila.push(token);
                }
            } else if(token.equals(")")){
                contador--;
                if(!pila.empty()){
                    while(true){
                        if(!pila.empty()){
                            if(!pila.peek().equals("(")){
                                prefija.add(pila.pop());
                            } else{
                                pila.pop();
                                break;
                            }
                        } else{
                            break;
                        }
                    }
                } else{
                    throw new ParentesisCierreException("Parentesis de cierre sobrante");
                }
            }

            if (contador<0){
                throw new ParentesisCierreException("Parentesis de cierre sobrante");
            }
        }
        while (!pila.empty()){
            if (!pila.peek().equals("(")){
                prefija.add(pila.pop());
            } else{
                throw new ParentesisAperturaException("Parentesis de apertura sobramte");
            }
        }
    }

    private boolean compararPresedencias(String opExp, String opTopPila){
        return darPresedenciaOperación(opExp) < darPresedenciaPila(opTopPila);
    }

    private int darPresedenciaOperación(String op){
        switch (op){
            case "*": case "/": case"%":
                return 2;
            case "+": case "-":
                return 1;
            default:
                return 0;
        }
    }

    private int darPresedenciaPila(String op){
        switch (op){
            case "*": case "/": case"%":
                return 2;
            case "+": case "-":
                return 1;
            default:
                return 0;
        }
    }

    public ArrayList<String> getPrefija(){
        return prefija;
    }
}
