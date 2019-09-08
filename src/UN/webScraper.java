package UN;

import static UN.webScraper.p;
import static UN.webScraper.raiz;
import static UN.webScraper.root;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.TextArea;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.text.MutableAttributeSet;
import javax.swing.text.html.HTML;
import javax.swing.text.html.HTML.Tag;
import javax.swing.text.html.HTMLEditorKit;
import javax.swing.tree.DefaultMutableTreeNode;

//clase auxiliar
public class webScraper {
    static String cadena="";
    static int op = 0;
    static Nodo3 root;
    static Nodo2 raiz, p;

    public static void marcar() {
        Nodo3 f = root, t;
        t = f.getLink();
        int i;
        while (f.getLink() != null) {
            i = 1;
            if (!f.isMarcado()) {
                while (t != null) {
                    if (f.getTag() == t.getTag()) {
                        i++;
                        t.setMarcado(true);
                    }
                    t = t.getLink();
                }
                if(i!=1){
                   
                    cadena=cadena+"El Tag " + f.getTag() + " aparece " + i + " veces.\n";
                }else{
                    
                    cadena=cadena+"El Tag " + f.getTag() + " aparece " + i + " vez.\n";
                }
                
            }
            f = f.getLink();
            if (f.getLink() != null) {
                t = f.getLink();
            }
        }
    }

    public static Nodo3 haf(int pos) {
        Nodo3 k = root;

        while (k != null) {
            if (k.getPos() == pos) {
                return k;
            }
            k = k.getLink();
        }
        return null;
    }

    public static Nodo2 dentro(Nodo3 g) {//hallar etiquetas de inicio contenidas dentro de una etiqueta y devuelve una lista tipo nodo2 con todas esas etiquetas
        Nodo2 dos = raiz, tres = null, aux = null;
        boolean b = false, b2 = false;
        while (dos != null) {
            if (dos.getPos() == g.getPos() && dos.getTag().equals(g.getTag()) && b == false) {
                b = true;
                dos = dos.getLink();
            }
            if (b) {
                if (dos.getNaturaleza() == 1 && dos.getPostagfinal() < g.getPosTagFinal() && dos.getPos() > g.getPos()) {
                    if (tres == null) {
                        tres = new Nodo2(dos.getTag(), dos.getNaturaleza(), dos.getPos());
                        aux = tres;
                    } else {
                        aux.setLink(new Nodo2(dos.getTag(), dos.getNaturaleza(), dos.getPos()));
                        aux = aux.getLink();
                    }
                }
            }
            dos = dos.getLink();
        }
        return tres;
    }

    public static void migracion(Nodo2 r) {//r es raiz la de Nodo2
        Nodo3 ayuda = null;
        while (r != null) {
            if (r.getNaturaleza() == 1) {
                Nodo3 guia = new Nodo3(r.getPostagfinal(), r.getTag(), r.getPos());
                guia.setSub(dentro(guia));
                if (root == null) {
                    root = guia;
                    ayuda = root;
                } else {
                    ayuda.setLink(guia);
                    ayuda = ayuda.getLink();
                }
            }
            r = r.getLink();
        }
    }

    public static void ordenador() {
        Nodo3 aux = root;
        Nodo2 corredor = aux.getSub();
        while (aux != null) {
            while (corredor != null) {
                busqueda(corredor, aux);
                corredor = corredor.getLink();
            }
            aux = aux.getLink();
            if (aux != null) {
                corredor = aux.getSub();
            }
        }
    }

    public static void busqueda(Nodo2 node, Nodo3 g) {
        Nodo2 corredor = g.getLink().getSub();
        Nodo3 aux = g;
        boolean b = false;
        while (aux != null && b == false) {
            while (corredor != null && b == false) {
                if (corredor.getPos() == node.getPos()) {
                    b = true;
                }
                corredor = corredor.getLink();
            }
            aux = aux.getLink();
            if (aux != null) {
                corredor = aux.getSub();
            }
        }
        if (b == false) {
            //Nodo3 NODE=new Nodo3(node.getTag(),node.getPos());
            //g.addson(NODE);
            g.addson(haf(node.getPos()));
        }
    }

    public static void crearbase() {
        Nodo2 j = raiz;
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter("base.txt"));
            while (j != null) {
                if (j.getNaturaleza() == 0) {
                    writer.write("</" + j.getTag() + ">" + ";" + j.getNaturaleza() + ";" + j.getPos() + ";" + j.getPostagfinal());
                } else {
                    writer.write("<" + j.getTag() + ">" + ";" + j.getNaturaleza() + ";" + j.getPos() + ";" + j.getPostagfinal());
                }
                writer.newLine();
                j = j.getLink();
            }
            writer.close();
        } catch (IOException ex) {
            Logger.getLogger(Parser.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public static void buscarTagFinal(Nodo2 n) {
        Nodo2 m = n.getLink();
        int h = 1;
        while (h != 0 && m != null) {
            if (m.getTag().equals(n.getTag())) {
                if (m.getNaturaleza() == 1) {
                    h++;
                } else {
                    h--;
                }
                if (h == 0) {
                    n.setPostagfinal(m.getPos());
                }
            }
            m = m.getLink();
        }
    }

    public static void main(String[] args) {
        try {
            String target =JOptionPane.showInputDialog("escriba la URL que deseas escrapear");
            URL my_url = new URL(target);
            rastrear(my_url);
            JFrame j = new muestra();
            JFrame b=new JFrame();
             b.setBounds(30, 30, 300, 300);
             b.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
             b.setVisible(true);
             TextArea c=new TextArea(0,30);
             c.setSize(100,100);
             c.setText(cadena);
             c.setEditable(false);
             c.setBackground(Color.white);
             b.add(c);
            j.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            j.setVisible(true);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void rastrear(URL url) {
        try {
            boolean esValida;
            try {
                URLConnection connection = url.openConnection();
                connection.connect();
                if ((connection.getContentType() != null) && !connection.getContentType().toLowerCase().startsWith("text/")) {
                    esValida = false;
                } else {
                    esValida = true;
                }
            } catch (IOException e) {
                esValida = false;
            }

            if (esValida) {
                BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()));
                HTMLEditorKit.Parser parse = new HTMLParse().getParser();
                parse.parse(br, new Parser(url), true);
                Nodo2 aux = raiz;

                while (aux != null) {
                    if (aux.getNaturaleza() == 1) {
                        buscarTagFinal(aux);
                    } else {
                        aux.setPostagfinal(-1);
                    }
                    aux = aux.getLink();
                }
                crearbase();
                Nodo2 l = raiz;
                /*while (l != null) {
                    System.out.println(l.getTag() + ";" + l.getNaturaleza() + ";" + l.getPos() + ";" + l.getPostagfinal());
                    l = l.getLink();
                }*/
               // System.out.println("**********************************************");
                l = raiz.getLink();
                migracion(raiz);
                Nodo3 ayu;
                ayu = root;
                ordenador();

                /*while (ayu != null) {
                    System.out.println("__________________" + ayu.getTag() + "___________________");
                    ayu.showsons();
                    ayu = ayu.getLink();
                }*/
                marcar();

                //mostrar(ayu);
                /* while (ayu != null) {//muestra todos los que están dentro de cada tag
                    System.out.println("___________" + ayu.getTag() + "_____________________");
                    Nodo2 muestra = ayu.getSub();
                    while (muestra != null) {
                        System.out.println(muestra.getTag());
                        muestra = muestra.getLink();
                    }
                    ayu = ayu.getLink();
                }*/
            } else {
                System.out.println("No es una página válida");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    /*public static void mostrar(Nodo3 r){
        op++;
        int y=0;
            System.out.println(op+":"+r.getTag());
            while(y<r.tamaño()){
                mostrar(r.hijo(y));
                y++;
            }
    }*/
}

class HTMLParse extends HTMLEditorKit {

    public HTMLEditorKit.Parser getParser() {
        return super.getParser();
    }
}

class muestra extends JFrame {

    DefaultMutableTreeNode r = new DefaultMutableTreeNode(root.getTag());
    
    public muestra() {
        setTitle("Arbol");
        setBounds(350, 300, 600, 600);
        Nodo3 p = root;
        recorrido(r, p);
        JTree k = new JTree(r);
        LaminaArbol m = new LaminaArbol(k);
        add(m);
        Container laminacontenido=getContentPane();
        laminacontenido.add(new JScrollPane(k));
    }

    public static void recorrido(DefaultMutableTreeNode f, Nodo3 p) {
        int i = 0;
        while (i < p.tamaño()) {
            p.hijo(i).getTag();
            DefaultMutableTreeNode o = new DefaultMutableTreeNode(p.hijo(i).getTag());
            f.add(o);
            recorrido(o, p.hijo(i));
            i++;
        }
    }
}

class LaminaArbol extends JPanel {

    public LaminaArbol(JTree miarbol) {
        setLayout(new BorderLayout());
        add(miarbol, BorderLayout.NORTH);

    }
}

//clase auxiliar
class Parser extends HTMLEditorKit.ParserCallback {

    protected URL url;
    boolean b;
    Tag tag;
    int i = 0;

    public Parser(URL base) {
        this.url = base;
    }

    public void handleStartTag(HTML.Tag t, MutableAttributeSet a, int pos) {
        i++;
        if (raiz == null) {
            raiz = new Nodo2(t, 1, i);
            p = raiz;
        } else {
            Nodo2 nod = new Nodo2(t, 1, i);
            p.setLink(nod);
            p = p.getLink();
        }

    }

    public void handleEndTag(HTML.Tag t, int pos) {
        i++;
        Nodo2 nod = new Nodo2(t, 0, i);
        nod.setPostagfinal(-1);
        p.setLink(nod);
        p = p.getLink();
    }

}
