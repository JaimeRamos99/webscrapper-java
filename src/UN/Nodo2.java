
package UN;

import javax.swing.text.html.HTML.Tag;

/**
 *
 * @author USUARIO
 */
public class Nodo2 {
    private Nodo2 link;
    private Tag tag;
    private int naturaleza; 
    private int postagfinal;
    private int pos;
    private Tag padre;

    public Tag getPadre() {
        return padre;
    }

    public void setPadre(Tag padre) {
        this.padre = padre;
    }

    
    public int getPostagfinal() {
        return postagfinal;
    }

    public int getPos() {
        return pos;
    }

    public Nodo2(Tag tag, int naturaleza, int pos) {
        this.tag = tag;
        this.naturaleza = naturaleza;
        this.pos=pos;
    }
    
    public Nodo2 getLink() {
        return link;
    }

    public void setLink(Nodo2 link) {
        this.link = link;
    }

    public void setPostagfinal(int postagfinal) {
        this.postagfinal = postagfinal;
    }

    public Tag getTag() {
        return tag;
    }

    public void setTag(Tag tag) {
        this.tag = tag;
    }

    public int getNaturaleza() {
        return naturaleza;
    }

    public void setNaturaleza(int naturaleza) {
        this.naturaleza = naturaleza;
    }
    
}
