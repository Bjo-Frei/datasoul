/*
 * HelpLicense.java
 *
 * Created on 22 November 2006, 23:19
 */

package datasoul.help;

import com.sun.java_cup.internal.internal_error;
import java.io.IOException;
import java.io.InputStream;

/**
 *
 * @author  samuelm
 */
public class HelpLicense extends javax.swing.JPanel {
    
    /** Creates new form HelpLicense */
    public HelpLicense() {
        initComponents();
        
        StringBuffer sb = new StringBuffer();
        InputStream gplis = getClass().getResourceAsStream("gpl.txt");
        int i;
        try {
            while ( (i=gplis.read()) > 0 ){
                sb.append((char)i);
            }
            gplis.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        txtGPL.setText(sb.toString());
        
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc=" Generated Code ">//GEN-BEGIN:initComponents
    private void initComponents() {
        jScrollPane1 = new javax.swing.JScrollPane();
        txtGPL = new javax.swing.JTextArea();

        txtGPL.setColumns(20);
        txtGPL.setRows(5);
        jScrollPane1.setViewportView(txtGPL);

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .addContainerGap()
                .add(jScrollPane1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 376, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .addContainerGap()
                .add(jScrollPane1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 276, Short.MAX_VALUE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea txtGPL;
    // End of variables declaration//GEN-END:variables
    
}
