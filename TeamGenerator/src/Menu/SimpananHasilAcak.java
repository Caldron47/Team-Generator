/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package Menu;

import java.awt.HeadlessException;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import static org.apache.pdfbox.pdmodel.font.Standard14Fonts.FontName.TIMES_ROMAN;

/**
 *
 * @author DELL
 */
public class SimpananHasilAcak extends javax.swing.JFrame {
    int jumlahData = 0;
    public String user;
    /**
     * Creates new form SimpananHasilAcak
     */
    public SimpananHasilAcak(String u) {
        setSize(700, 600);
        setLocationRelativeTo(null);
        initComponents();
        this.user = u;
        tampilkanData();
    }
    private void tampilkanData(){
        try{
            
    // Mengambil semua informasi hasil acak yang disimpan oleh user   
            String sql = "SELECT * FROM hasil_acak where username = '" + user +"'";
            java.sql.Connection conn = (Connection)Config.configDB();
            java.sql.Statement stm = conn.createStatement();
            java.sql.ResultSet res = stm.executeQuery(sql);
                  
    // Mengosongkan tampilan Text Area      
            txaDataTersimpan.setText("");
            while(res.next()){
                String[] hasilAcakSplit = res.getString("hasil").split(",");
                jumlahData++;
                
    // Menampilkan judul hasil acak         
                txaDataTersimpan.append("Judul hasil acak: " + res.getString("judul_hasil") + "\n");
                
    // Menampilkan urutan hasil acak
                txaDataTersimpan.append("Hasil acak ke-" + res.getInt("urutan_hasil"));
                
    // Menampilkan jumlah anggota per tim
                txaDataTersimpan.append("\nJumlah Anggota per Tim: " + res.getInt("jml_anggota"));
                
                ArrayList<String> hasilAcak = new ArrayList<String>(Arrays.asList(hasilAcakSplit));
                txaDataTersimpan.append("\nHasil Pembagian Tim:\n");
                int j = 0;
                int no = 0;
                
                if (res.getString("hasil").equals("")) {
                    txaDataTersimpan.append("Tidak ada hasil yang disimpan\n");
                } else {
                    txaDataTersimpan.append(1 + ").  ");
                    
    // Perulangan untuk menampilkan tim hasil pengacakan dengan diawali nomor urut di setiap tim
                    for (int i = 0; i < hasilAcak.size(); i++) {
                        txaDataTersimpan.append(hasilAcak.get(i));
                        j++;

                        if(j == res.getInt("jml_anggota")){
                            no++;
                            j=0;
                            txaDataTersimpan.append("\n");
                            if (i + 1 < hasilAcak.size()) {
                                txaDataTersimpan.append(no + 1 + ").  ");
                            }
                            continue;
                        }
                        if (i + 1 == hasilAcak.size()) {
                            txaDataTersimpan.append("\n");
                        } else {
                            txaDataTersimpan.append(" ~ ");
                        }
                    }
                }
                if (jumlahData > 0) {
                    
    // Menampilkan pemisah antara satu hasil acak dengan yang lain 
                    txaDataTersimpan.append("===================================================");
                }
                txaDataTersimpan.append("\n");
            }
            
    // Mengubah JLabel agar menampilkan jumlah hasil acak yang ditampilkan        
            lbJmlData.setText(Integer.toString(jumlahData));
            jumlahData = 0;
           }catch(HeadlessException | SQLException e){
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }
    public void tampilkanSatu() {
        
    // Mengosongkan tampilan Text Area    
        txaDataTersimpan.setText("");
        try {
            
    // Mengambil hasil acakr dari database sesuai dengan nomor urut yang telah diinputkan user        
            String sql = "SELECT * FROM hasil_acak where urutan_hasil = " + txTampilkanSatu.getText() + " AND username = '" + user +"'";
            java.sql.Connection conn = (Connection)Config.configDB();
            java.sql.Statement stm = conn.createStatement();
            java.sql.ResultSet res = stm.executeQuery(sql);
            res.next();
            
    // Menampilkan hasil acak dari database ke Text Area serta informasi lain
    // seperti judul, urutan hasil acak, dan jumlah anggota per tim
            String[] hasilAcakSplit = res.getString("hasil").split(",");
            txaDataTersimpan.append("Judul hasil acak: " + res.getString("judul_hasil") + "\n");
            txaDataTersimpan.append("Hasil acak ke-" + res.getInt("urutan_hasil"));
            txaDataTersimpan.append("\nJumlah Anggota per Tim: " + res.getInt("jml_anggota"));

            ArrayList<String> hasilAcak = new ArrayList<String>(Arrays.asList(hasilAcakSplit));
            txaDataTersimpan.append("\nHasil Pembagian Tim:\n");
            int j = 0;
            int no = 0;

            if (res.getString("hasil").equals("")) {
                txaDataTersimpan.append("Tidak ada hasil yang disimpan\n");
            } else {
                txaDataTersimpan.append(1 + ").  ");
                for (int i = 0; i < hasilAcak.size(); i++) {
                    txaDataTersimpan.append(hasilAcak.get(i));
                    j++;

                    if(j == res.getInt("jml_anggota")){
                        no++;
                        j=0;
                        txaDataTersimpan.append("\n");
                        if (i + 1 < hasilAcak.size()) {
                            txaDataTersimpan.append(no + 1 + ").  ");
                        }
                        continue;
                    }
                    if (i + 1 == hasilAcak.size()) {
                        txaDataTersimpan.append("\n");
                    } else {
                        txaDataTersimpan.append(" ~ ");
                    }
                }
            }
        }catch(HeadlessException | SQLException e){
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }
    public void hapusSatuData() {
        try{
            
    // Mengambil nilai tertinggi dari kolom urutan_hasil dengan username user yang
    // sedang login       
            String sql = "SELECT MAX(urutan_hasil) FROM hasil_acak where username = '" + user +"'";
            java.sql.Connection conn = (Connection)Config.configDB();
            java.sql.Statement stmUrutanData = conn.createStatement();
            java.sql.ResultSet resUrutanData = stmUrutanData.executeQuery(sql);
            resUrutanData.next();
            if (Integer.parseInt(txHapusSatu.getText()) > 0 && Integer.parseInt(txHapusSatu.getText()) <= resUrutanData.getInt(1)) {
                
    // Menghapus baris berdasarkan nomor urut hasil acak yang diinputkan user            
                sql = "DELETE FROM hasil_acak WHERE urutan_hasil =" + txHapusSatu.getText() + " AND username = '" + user +"'";
                java.sql.PreparedStatement pstm = conn.prepareStatement(sql);
                pstm.execute();
                JOptionPane.showMessageDialog(null, "Daftar nomor " + txHapusSatu.getText() + " berhasil dihapus");
                int j;
                for (int i = Integer.parseInt(txHapusSatu.getText()) + 1; i <= resUrutanData.getInt(1); i++) {
                    j = i - 1;
                    
    // Memperbaharui kolom urutan_hasil agar terurut dengan benar setelah
    // salah satu baris dihapus                
                    sql = "UPDATE hasil_acak SET urutan_hasil = " + j + " WHERE urutan_hasil = " + i + " AND username = '" + user +"'";
                    java.sql.PreparedStatement pstm2 = conn.prepareStatement(sql);
                    pstm2.execute();
                }
            } else {
                JOptionPane.showMessageDialog(null, "Input yang Anda masukkan (" + txHapusSatu.getText() + ") tidak cocok dengan nomor daftar manapun!");
            }
            tampilkanData();
        }catch(HeadlessException | SQLException e){
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jButton11 = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        txaDataTersimpan = new javax.swing.JTextArea();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        lbJmlData = new javax.swing.JLabel();
        btnHapusSemua = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        btnPrint = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        txTampilkanSatu = new javax.swing.JTextField();
        btnTampilkanSatu = new javax.swing.JButton();
        btnTampilkanSemua = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        txHapusSatu = new javax.swing.JTextField();
        btnHapusSatu = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        jPanel1.setBackground(new java.awt.Color(255, 255, 0));

        jLabel3.setBackground(new java.awt.Color(255, 255, 0));
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("Hasil Acak Tersimpan");

        jButton11.setBackground(new java.awt.Color(255, 255, 0));
        jButton11.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Gambar/output-onlinepngtools (4).png"))); // NOI18N
        jButton11.setBorder(null);
        jButton11.setBorderPainted(false);
        jButton11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton11ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButton11, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel1Layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, 640, Short.MAX_VALUE)
                    .addContainerGap()))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jButton11, javax.swing.GroupLayout.DEFAULT_SIZE, 42, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel1Layout.createSequentialGroup()
                    .addGap(10, 10, 10)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(10, Short.MAX_VALUE)))
        );

        jPanel3.setBackground(new java.awt.Color(255, 153, 0));

        txaDataTersimpan.setColumns(20);
        txaDataTersimpan.setRows(5);
        jScrollPane2.setViewportView(txaDataTersimpan);

        jLabel1.setText("Jumlah hasil acak yang disimpan :");

        lbJmlData.setText("0");

        btnHapusSemua.setBackground(new java.awt.Color(255, 0, 0));
        btnHapusSemua.setText("Hapus Semua Data");
        btnHapusSemua.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHapusSemuaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 640, Short.MAX_VALUE)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lbJmlData)
                        .addGap(3, 3, 3)
                        .addComponent(jLabel2)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(211, 211, 211)
                        .addComponent(btnHapusSemua, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2)
                    .addComponent(lbJmlData))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 268, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnHapusSemua, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(14, 14, 14))
        );

        jPanel2.setBackground(new java.awt.Color(255, 204, 0));

        btnPrint.setBackground(new java.awt.Color(255, 102, 0));
        btnPrint.setText("Cetak Hasil Acak yang Ditampilkan");
        btnPrint.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPrintActionPerformed(evt);
            }
        });

        jLabel4.setText("Masukkan nomor hasil acak untuk ditampilkan :");

        txTampilkanSatu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txTampilkanSatuActionPerformed(evt);
            }
        });

        btnTampilkanSatu.setBackground(new java.awt.Color(255, 102, 0));
        btnTampilkanSatu.setText("Tampilkan");
        btnTampilkanSatu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTampilkanSatuActionPerformed(evt);
            }
        });

        btnTampilkanSemua.setBackground(new java.awt.Color(255, 102, 0));
        btnTampilkanSemua.setText("Tampilkan Semua Hasil");
        btnTampilkanSemua.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTampilkanSemuaActionPerformed(evt);
            }
        });

        jLabel7.setText("Nomor hasil acak yang ingin dihapus :");

        txHapusSatu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txHapusSatuActionPerformed(evt);
            }
        });

        btnHapusSatu.setBackground(new java.awt.Color(255, 102, 0));
        btnHapusSatu.setText("Hapus");
        btnHapusSatu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHapusSatuActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(btnPrint, javax.swing.GroupLayout.PREFERRED_SIZE, 302, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 36, Short.MAX_VALUE)
                        .addComponent(btnTampilkanSemua, javax.swing.GroupLayout.PREFERRED_SIZE, 302, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel4)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel7)
                                .addGap(57, 57, 57)))
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(txHapusSatu)
                            .addComponent(txTampilkanSatu, javax.swing.GroupLayout.DEFAULT_SIZE, 120, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnHapusSatu, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnTampilkanSatu, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnTampilkanSatu, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txTampilkanSatu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnHapusSatu, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txHapusSatu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnPrint, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnTampilkanSemua, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton11ActionPerformed

    // Melakukan instansiasi object dari kelas HalamanUtama untuk 
    // menampilkannya serta memasukkan variabel yang berisi username 
    // yang sedang login ke variabel object HalamanUtama    
        HalamanUtama h = new HalamanUtama();
        h.user = this.user;
        h.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jButton11ActionPerformed

    private void txHapusSatuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txHapusSatuActionPerformed
        hapusSatuData();
    }//GEN-LAST:event_txHapusSatuActionPerformed

    private void btnHapusSatuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHapusSatuActionPerformed
        hapusSatuData();
    }//GEN-LAST:event_btnHapusSatuActionPerformed

    private void btnHapusSemuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHapusSemuaActionPerformed

    // Menghapus semua baris pada tabel hasil_acak yang memiliki kolom 
    // username bernilai sama dengan username user yang sedang login   
        try{
            String sql = "DELETE FROM hasil_acak where username = '" + user +"'";
            java.sql.Connection conn = (Connection)Config.configDB();
            java.sql.PreparedStatement pstm = conn.prepareStatement(sql);
            pstm.execute();
        }catch(HeadlessException | SQLException e){
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
        tampilkanData();
    }//GEN-LAST:event_btnHapusSemuaActionPerformed

    private void btnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPrintActionPerformed

    // Memasukkan tiap baris konten yang ditampilkan Text Area 
    // ke dalam Array String
        String s[] = txaDataTersimpan.getText().split("\\r?\\n");
        if ((s == null || s.length <= 0) || s[0].equals("")) {
            JOptionPane.showMessageDialog(this, "Tidak ada data yang ditampilkan, tidak dapat melakukan Print!");                                         
        } else {
            try {
                
    // Instansiasi object dari kelas JFileChooser untuk 
    // menampilkan dialog simpan file agar user dapat memilih 
    // direktori untuk menyimpan file PDF hasil acak            
                JFileChooser c = new JFileChooser();
                c.setDialogTitle("Pilih direktori untuk menyimpan file Anda");
                c.showSaveDialog(null);
                
                String title = "HASIL ACAK TEAM GENERATOR";
                PDType1Font font = new PDType1Font(TIMES_ROMAN);
                int marginTop = 30;
                int fontSize = 18;
                
    // Instansiasi berbagai object dari kelas-kelas yang 
    // ada pada library PDFBox
                final PDDocument doc = new PDDocument();
                PDPage page = new PDPage(PDRectangle.A4);
                PDRectangle mediaBox = page.getMediaBox();
                doc.addPage(page);
                PDPageContentStream contentStream = new PDPageContentStream(doc, page);

                float titleWidth = font.getStringWidth(title) / 1000 * fontSize;
                float titleHeight = font.getFontDescriptor().getFontBoundingBox().getHeight() / 1000 * fontSize;

                float startX = (mediaBox.getWidth() - titleWidth) / 2;
                float startY = mediaBox.getHeight() - marginTop - titleHeight;

                contentStream.beginText();
                contentStream.setFont(font, fontSize);
                contentStream.newLineAtOffset(startX, startY);
                contentStream.showText(title);
                contentStream.endText();

                contentStream.beginText();
                contentStream.setLeading(14.5f);
                contentStream.setFont(font, 14);
                contentStream.newLineAtOffset(50, 750);
                
                int baris=1;
                
    // Perulangan untuk mencetak tiap baris Text Area ke file PDF        
                for (int i = 0; i < s.length; i++) {
                    
    // Pengkondisian agar sistem mencetak ke baris baru dan baris 
    // yang terpotong ditambahkan karakter strip (-) jika terdapat 
    // baris yang memiliki lebih dari atau sama dengan 100 karakter
                    if (s[i].length() >= 70) {
                        String[] split = s[i].split("");
                        for (int j = 0; j < split.length; j++) {
                            contentStream.showText(split[j]);
                            if (j % 70 == 0 && j != 0) {
                                contentStream.showText("-");
                                baris++;
                                contentStream.newLine();
                            }
                        }
                        baris++;
                        contentStream.newLine();
                        continue;
                    }
                    contentStream.showText(s[i]);
                    baris++;
                    contentStream.newLine();

    // Pengkondisian agar sistem membuat halaman PDF baru dan melanjutkan 
    // pencetakan di sana jika sudah terdapat 42 baris atau lebih di 
    // halaman sebelumnya    
                    if (baris >= 47) {
                        contentStream.endText();
                        contentStream.close();
                        PDPage page2 = new PDPage(PDRectangle.A4);
                        doc.addPage(page2);
                        baris=0;
                        contentStream = new PDPageContentStream(doc, page2);
                        contentStream.beginText();
                        contentStream.setLeading(14.5f);
                        contentStream.newLineAtOffset(50, 750);
                        contentStream.setFont(font, 14);
                    }
                }
                contentStream.endText();            
                contentStream.close();

                try {
                    doc.save(c.getSelectedFile().getPath() + ".pdf");
                    JOptionPane.showMessageDialog(null, "Proses penyimpanan hasil acak ke pdf berhasil!");
                    doc.close();
                } catch (IOException ex) {
                    Logger.getLogger(SimpananHasilAcak.class.getName()).log(Level.SEVERE, null, ex);
                }
            } catch (IOException ex) {
                Logger.getLogger(SimpananHasilAcak.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_btnPrintActionPerformed

    private void btnTampilkanSatuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTampilkanSatuActionPerformed
        tampilkanSatu();
    }//GEN-LAST:event_btnTampilkanSatuActionPerformed

    private void txTampilkanSatuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txTampilkanSatuActionPerformed
        tampilkanSatu();
    }//GEN-LAST:event_txTampilkanSatuActionPerformed

    private void btnTampilkanSemuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTampilkanSemuaActionPerformed
        tampilkanData();
    }//GEN-LAST:event_btnTampilkanSemuaActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(SimpananHasilAcak.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(SimpananHasilAcak.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(SimpananHasilAcak.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(SimpananHasilAcak.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new SimpananHasilAcak(" ").setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnHapusSatu;
    private javax.swing.JButton btnHapusSemua;
    private javax.swing.JButton btnPrint;
    private javax.swing.JButton btnTampilkanSatu;
    private javax.swing.JButton btnTampilkanSemua;
    private javax.swing.JButton jButton11;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lbJmlData;
    private javax.swing.JTextField txHapusSatu;
    private javax.swing.JTextField txTampilkanSatu;
    private javax.swing.JTextArea txaDataTersimpan;
    // End of variables declaration//GEN-END:variables
}
