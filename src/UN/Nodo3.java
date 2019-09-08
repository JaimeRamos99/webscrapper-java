package UN;

import java.util.LinkedList;
import javax.swing.text.html.HTML.Tag;

public class Nodo3 {

    private Nodo2 sub;
    private Nodo3 link;
    private int posTagFinal;
    private Tag tag;
    private int pos;
    private LinkedList<Nodo3> lista;
    private boolean marcado;  

    public boolean isMarcado() {
        return marcado;
    }

    public void setMarcado(boolean marcado) {
        this.marcado = marcado;
    }
   
    public void addson(Nodo3 n) {
        lista.add(n);
    }
    public int tamaño(){
        return lista.size();
    }
    public Nodo3 hijo(int i){
        return lista.get(i);
    }
    public void showsons() {
        int i = 0;
        if(lista.size()>0){
        while (i < lista.size()) {
            System.out.println(lista.get(i).getTag());
            i++;
        }
    }
    }

    public int getPos() {
        return pos;
    }

    public Tag getTag() {
        return tag;
    }

    public int getPosTagFinal() {
        return posTagFinal;
    }

    public Nodo3 getLink() {
        return link;
    }

    public void setLink(Nodo3 link) {
        this.link = link;
    }
    public Nodo3(Tag t,int pos){
        this.tag =t;
        this.pos = pos;
        lista=new LinkedList<>();
        marcado=false;
    }
    
    public Nodo3(int postagfinal, Tag tag, int pos) {
        this.posTagFinal = postagfinal;
        this.tag = tag;
        this.pos = pos;
        lista=new LinkedList<>();
        marcado=false;
    }

    public Nodo2 getSub() {
        return sub;
    }

    public void setSub(Nodo2 sub) {
        this.sub = sub;
    }
}
