import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class FormPemesanan extends JFrame implements ActionListener {

    JTextField tfNama, tfNoHp;
    JTextArea taStruk;
    JComboBox<String> cbMenu, cbJumlah;
    JRadioButton rbKecil, rbSedang, rbBesar;
    JCheckBox cbEsTeh, cbKerupuk;
    JButton btnPesan, btnReset;
    JLabel lblStatus;

    String[] namaMenu = {"Nasi Goreng", "Mie Ayam", "Ayam Geprek", "Soto Ayam", "Bakso"};
    int[] hargaMenu = {15000, 13000, 17000, 14000, 15000};

    public FormPemesanan() {
        setTitle("Warung Makan Bu Rahayu - Form Pemesanan");
        setSize(850, 420);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);

        setLayout(new BorderLayout(10, 10));

        JLabel judul = new JLabel("FORM PEMESANAN MAKANAN", SwingConstants.CENTER);
        judul.setFont(new Font("Arial", Font.BOLD, 22));
        judul.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
        add(judul, BorderLayout.NORTH);

        JPanel panelForm = new JPanel(new GridLayout(6, 2, 5, 10));
        panelForm.setBorder(BorderFactory.createTitledBorder("Data Pesanan"));

        tfNama = new JTextField(15);
        tfNoHp = new JTextField();

        cbMenu = new JComboBox<>(namaMenu);

        String[] daftarJumlah = {"1", "2", "3", "4", "5"};
        cbJumlah = new JComboBox<>(daftarJumlah);

        rbKecil = new JRadioButton("Kecil");
        rbSedang = new JRadioButton("Sedang", true);
        rbBesar = new JRadioButton("Besar");
        ButtonGroup grupPorsi = new ButtonGroup();
        grupPorsi.add(rbKecil);
        grupPorsi.add(rbSedang);
        grupPorsi.add(rbBesar);

        JPanel panelPorsi = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        panelPorsi.add(rbKecil);
        panelPorsi.add(rbSedang);
        panelPorsi.add(rbBesar);

        cbEsTeh = new JCheckBox("Es Teh");
        cbKerupuk = new JCheckBox("Kerupuk");
        JPanel panelTambahan = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        panelTambahan.add(cbEsTeh);
        panelTambahan.add(cbKerupuk);

        panelForm.add(new JLabel("Nama Pemesan"));
        panelForm.add(tfNama);
        panelForm.add(new JLabel("No. HP"));
        panelForm.add(tfNoHp);
        panelForm.add(new JLabel("Menu"));
        panelForm.add(cbMenu);
        panelForm.add(new JLabel("Jumlah"));
        panelForm.add(cbJumlah);
        panelForm.add(new JLabel("Ukuran Porsi"));
        panelForm.add(panelPorsi);
        panelForm.add(new JLabel("Tambahan"));
        panelForm.add(panelTambahan);

        add(panelForm, BorderLayout.WEST);

        taStruk = new JTextArea();
        taStruk.setEditable(false);
        JScrollPane scrollStruk = new JScrollPane(taStruk);
        scrollStruk.setBorder(BorderFactory.createTitledBorder("Struk Pesanan"));
        add(scrollStruk, BorderLayout.CENTER);

        JPanel panelBawah = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 8));
        btnPesan = new JButton("Pesan Sekarang");
        btnReset = new JButton("Reset");
        lblStatus = new JLabel("Silakan isi pesanan");

        panelBawah.add(btnPesan);
        panelBawah.add(btnReset);
        panelBawah.add(lblStatus);
        add(panelBawah, BorderLayout.SOUTH);

        btnPesan.addActionListener(this);
        btnReset.addActionListener(this);

        btnPesan.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                btnPesan.setBackground(Color.ORANGE);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                btnPesan.setBackground(null);
            }
        });

        tfNoHp.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                if (!Character.isDigit(c)) {
                    e.consume();
                }
            }
        });

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                int pilih = JOptionPane.showConfirmDialog(FormPemesanan.this,
                        "Yakin mau keluar?", "Konfirmasi", JOptionPane.YES_NO_OPTION);
                if (pilih == JOptionPane.YES_OPTION) {
                    dispose();
                }
            }
        });
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnPesan) {
            prosesPesanan();
        } else if (e.getSource() == btnReset) {
            resetForm();
        }
    }

    private int hitungTotal() {
        int harga = hargaMenu[cbMenu.getSelectedIndex()];

        if (rbKecil.isSelected()) {
            harga -= 2000;
        } else if (rbBesar.isSelected()) {
            harga += 3000;
        }

        if (cbEsTeh.isSelected()) harga += 4000;
        if (cbKerupuk.isSelected()) harga += 2000;

        int jumlah = Integer.parseInt((String) cbJumlah.getSelectedItem());
        return harga * jumlah;
    }

    private void prosesPesanan() {
        String nama = tfNama.getText().trim();
        String noHp = tfNoHp.getText().trim();

        if (nama.isEmpty() || noHp.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Nama dan No. HP tidak boleh kosong!",
                    "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String porsi = "Sedang";
        if (rbKecil.isSelected()) porsi = "Kecil";
        if (rbBesar.isSelected()) porsi = "Besar";

        String tambahan = "";
        if (cbEsTeh.isSelected()) tambahan += "Es Teh ";
        if (cbKerupuk.isSelected()) tambahan += "Kerupuk ";
        if (tambahan.isEmpty()) tambahan = "-";

        taStruk.append("Nama     : " + nama + "\n");
        taStruk.append("No. HP   : " + noHp + "\n");
        taStruk.append("Menu     : " + cbMenu.getSelectedItem() + " x" + cbJumlah.getSelectedItem() + "\n");
        taStruk.append("Porsi    : " + porsi + "\n");
        taStruk.append("Tambahan : " + tambahan + "\n");
        taStruk.append("TOTAL    : Rp " + hitungTotal() + "\n");
        taStruk.append("----------------------------\n");

        lblStatus.setText("Pesanan " + nama + " diterima");
    }

    private void resetForm() {
        tfNama.setText("");
        tfNoHp.setText("");
        cbMenu.setSelectedIndex(0);
        cbJumlah.setSelectedIndex(0);
        rbSedang.setSelected(true);
        cbEsTeh.setSelected(false);
        cbKerupuk.setSelected(false);
        taStruk.setText("");
        lblStatus.setText("Form sudah direset");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            FormPemesanan form = new FormPemesanan();
            form.setVisible(true);
        });
    }
}
