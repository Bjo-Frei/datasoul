/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * TextSplitPanel.java
 *
 * Created on Apr 13, 2009, 10:11:02 PM
 */

package datasoul.util;

import datasoul.datashow.TextServiceItem;
import javax.swing.text.JTextComponent;
import javax.swing.JOptionPane;

/**
 *
 * @author samuel
 */
public class TextSplitPanel extends javax.swing.JPanel {

    private JTextComponent textComp;

    /** Creates new form TextSplitPanel */
    public TextSplitPanel() {
        initComponents();
    }

    public void registerTextArea(JTextComponent textComp){
        this.textComp = textComp;
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        cbMaxLenght = new javax.swing.JCheckBox();
        cbMaxSlideLines = new javax.swing.JCheckBox();
        txtMaxSlideLines = new javax.swing.JTextField();
        txtMaxLenght = new javax.swing.JTextField();
        btnOk = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();

        cbMaxLenght.setText("Split in lines of");

        cbMaxSlideLines.setSelected(true);
        cbMaxSlideLines.setText("Split in slides of");

        txtMaxSlideLines.setText("5");

        txtMaxLenght.setText("80");

        btnOk.setText("Split");
        btnOk.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnOkActionPerformed(evt);
            }
        });

        jLabel2.setText("characters");

        jLabel1.setText("lines");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(cbMaxLenght)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtMaxLenght, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(4, 4, 4)
                        .addComponent(jLabel2))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(cbMaxSlideLines)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtMaxSlideLines, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(4, 4, 4)
                        .addComponent(jLabel1)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 169, Short.MAX_VALUE)
                .addComponent(btnOk))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cbMaxLenght)
                    .addComponent(txtMaxLenght, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cbMaxSlideLines)
                    .addComponent(txtMaxSlideLines, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1)))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnOk))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnOkActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOkActionPerformed

        String str0;
        str0 = textComp.getText();

        int lines;
        try{
            lines = Integer.parseInt(txtMaxSlideLines.getText());
        }catch(Exception e){
            JOptionPane.showMessageDialog(this,"Invalid lines per slide value",java.util.ResourceBundle.getBundle("datasoul/internationalize").getString("Datasoul_Error"),0);
            return;
        }

        int maxline;
        try {
            maxline = Integer.parseInt(txtMaxLenght.getText());
        }catch(Exception e){
            JOptionPane.showMessageDialog(this,"Invalid characters per line value",java.util.ResourceBundle.getBundle("datasoul/internationalize").getString("Datasoul_Error"),0);
            return;
        }


        // If needed remove slide breaks
        if ( cbMaxSlideLines.isSelected() ){
            str0 = str0.replaceAll("\n"+TextServiceItem.SLIDE_BREAK+"\n", "\n");
        }

        // If needed, remove line breaks
        if ( cbMaxLenght.isSelected() ){
            str0 = str0.replaceAll("\n"+TextServiceItem.SLIDE_BREAK+"\n", "@@"+TextServiceItem.SLIDE_BREAK+"@@");
            str0 = str0.replaceAll("\n"+TextServiceItem.CHORUS_MARK+"\n", "@@"+TextServiceItem.CHORUS_MARK+"@@");
            str0 = str0.replaceAll("\n", " ");
            str0 = str0.replaceAll("@@"+TextServiceItem.CHORUS_MARK+"@@", "\n"+TextServiceItem.CHORUS_MARK+"\n");
            str0 = str0.replaceAll("@@"+TextServiceItem.SLIDE_BREAK+"@@", "\n"+TextServiceItem.SLIDE_BREAK+"\n");
        }
        
        // if needed, break lines respecting maximum line width
        if ( cbMaxLenght.isSelected()){
            StringBuffer sb  = new StringBuffer();
            String tmp;
            int lastspace = 0;
            int lastbreak = 0;
            for (int i=0; i<str0.length(); i++){
                if ( str0.charAt(i) == ' '){
                    lastspace = i;
                }
                if ( (i-lastbreak) >  maxline){
                    tmp = str0.substring(lastbreak, lastspace).trim();
                    if (tmp.length() > 0){
                        sb.append(tmp+"\n");
                    }
                    lastbreak = lastspace;
                }
                if (str0.charAt(i) == '\n'){
                    tmp = str0.substring(lastbreak, i).trim();
                    if (tmp.length() > 0){
                        sb.append(tmp+"\n");
                    }
                    lastbreak = i;
                }
            }
            tmp = str0.substring(lastbreak).trim();
            if (tmp.length() > 0){
                sb.append(tmp+"\n");
            }

            str0 = sb.toString();
        }

        // Split in slides
        if ( cbMaxSlideLines.isSelected()){
            str0 = str0.replace("\n"+TextServiceItem.SLIDE_BREAK+"\n","\n");
        
            StringBuffer sb = new StringBuffer();

            String verses[] = str0.split(TextServiceItem.CHORUS_MARK+"\n");
            for (int i=0; i<verses.length; i++){
                String vlines[] = verses[i].split("\n");
                for (int j=0; j<vlines.length; j++){
                    sb.append(vlines[j]);
                    sb.append("\n");
                    if (lines != 0 && (j+1)%lines == 0 && vlines.length-j>1){
                        sb.append(TextServiceItem.SLIDE_BREAK);
                        sb.append("\n");
                    }
                }
                if (i < verses.length -1){
                    sb.append(TextServiceItem.CHORUS_MARK);
                    sb.append("\n");
                }
            }
            str0 = sb.toString();
        }

        // Store the result
        textComp.setText(str0);

    }//GEN-LAST:event_btnOkActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnOk;
    private javax.swing.JCheckBox cbMaxLenght;
    private javax.swing.JCheckBox cbMaxSlideLines;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JTextField txtMaxLenght;
    private javax.swing.JTextField txtMaxSlideLines;
    // End of variables declaration//GEN-END:variables

}
