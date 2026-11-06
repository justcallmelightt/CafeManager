import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.RenderingHints;
import java.text.NumberFormat;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;

@SuppressWarnings("serial")
public class CafeManagerFrame extends JFrame {
    private static final Color BACKGROUND = new Color(246, 244, 239);
    private static final Color SURFACE = Color.WHITE;
    private static final Color TEXT = new Color(28, 28, 30);
    private static final Color SECONDARY_TEXT = new Color(110, 110, 115);
    private static final Color ACCENT = new Color(43, 41, 38);
    private static final Font TITLE_FONT = new Font("Apple SD Gothic Neo", Font.BOLD, 28);
    private static final Font HEADING_FONT = new Font("Apple SD Gothic Neo", Font.BOLD, 17);
    private static final Font BODY_FONT = new Font("Apple SD Gothic Neo", Font.PLAIN, 14);

    private final Menu[] menus = {
        new Drink("아메리카노", 3000, 10),
        new Drink("카페라떼", 4000, 8),
        new Dessert("쿠키", 2000, 5),
        new Dessert("치즈케이크", 4500, 4)
    };
    private final int[] quantities = new int[menus.length];
    private final JPanel orderItemsPanel = new JPanel();
    private final JLabel totalPriceLabel = new JLabel("0원", SwingConstants.RIGHT);

    @SuppressWarnings("this-escape")
    public CafeManagerFrame() {
        setTitle("CafeManager");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(960, 620));
        setSize(1080, 680);
        setLocationRelativeTo(null);

        JPanel root = new JPanel(new BorderLayout());
        root.setBackground(BACKGROUND);
        root.add(createHeader(), BorderLayout.NORTH);
        root.add(createContent(), BorderLayout.CENTER);
        setContentPane(root);

        updateOrderSummary();
    }

    private JPanel createHeader() {
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(SURFACE);
        header.setBorder(BorderFactory.createEmptyBorder(22, 32, 22, 32));

        JLabel brand = new JLabel("CafeManager");
        brand.setFont(new Font("Apple SD Gothic Neo", Font.BOLD, 20));
        brand.setForeground(TEXT);

        JLabel stage = new JLabel("주문하기");
        stage.setFont(BODY_FONT);
        stage.setForeground(SECONDARY_TEXT);

        header.add(brand, BorderLayout.WEST);
        header.add(stage, BorderLayout.EAST);
        return header;
    }

    private JPanel createContent() {
        JPanel content = new JPanel(new BorderLayout(24, 0));
        content.setOpaque(false);
        content.setBorder(BorderFactory.createEmptyBorder(28, 32, 32, 32));
        content.add(createMenuArea(), BorderLayout.CENTER);
        content.add(createOrderArea(), BorderLayout.EAST);
        return content;
    }

    private JPanel createMenuArea() {
        JPanel area = new JPanel(new BorderLayout(0, 20));
        area.setOpaque(false);

        JPanel titleArea = new JPanel();
        titleArea.setOpaque(false);
        titleArea.setLayout(new BoxLayout(titleArea, BoxLayout.Y_AXIS));

        JLabel title = new JLabel("오늘의 메뉴");
        title.setFont(TITLE_FONT);
        title.setForeground(TEXT);
        JLabel subtitle = new JLabel("원하는 메뉴를 골라 주문에 담아보세요.");
        subtitle.setFont(BODY_FONT);
        subtitle.setForeground(SECONDARY_TEXT);
        subtitle.setBorder(BorderFactory.createEmptyBorder(6, 0, 0, 0));

        titleArea.add(title);
        titleArea.add(subtitle);
        area.add(titleArea, BorderLayout.NORTH);

        JPanel cards = new JPanel(new GridLayout(2, 2, 16, 16));
        cards.setOpaque(false);
        for (int i = 0; i < menus.length; i++) {
            cards.add(createMenuCard(menus[i], i));
        }
        area.add(cards, BorderLayout.CENTER);
        return area;
    }

    private JPanel createMenuCard(Menu menu, int menuIndex) {
        RoundedPanel card = new RoundedPanel(24, SURFACE);
        card.setLayout(new BorderLayout());
        card.setBorder(BorderFactory.createEmptyBorder(22, 22, 20, 22));

        JPanel copy = new JPanel();
        copy.setOpaque(false);
        copy.setLayout(new BoxLayout(copy, BoxLayout.Y_AXIS));

        JLabel category = new JLabel(menu instanceof Drink ? "COFFEE" : "DESSERT");
        category.setFont(new Font("Dialog", Font.BOLD, 11));
        category.setForeground(SECONDARY_TEXT);
        JLabel name = new JLabel(menu.getName());
        name.setFont(HEADING_FONT);
        name.setForeground(TEXT);
        name.setBorder(BorderFactory.createEmptyBorder(8, 0, 0, 0));
        JLabel price = new JLabel(formatMoney(menu.getPrice()));
        price.setFont(BODY_FONT);
        price.setForeground(SECONDARY_TEXT);
        price.setBorder(BorderFactory.createEmptyBorder(5, 0, 0, 0));

        copy.add(category);
        copy.add(name);
        copy.add(price);
        card.add(copy, BorderLayout.NORTH);

        JButton addButton = createButton("담기", ACCENT, Color.WHITE);
        addButton.addActionListener(event -> addMenu(menuIndex));
        card.add(addButton, BorderLayout.SOUTH);
        return card;
    }

    private JPanel createOrderArea() {
        RoundedPanel orderArea = new RoundedPanel(26, SURFACE);
        orderArea.setPreferredSize(new Dimension(340, 0));
        orderArea.setLayout(new BorderLayout());
        orderArea.setBorder(BorderFactory.createEmptyBorder(24, 24, 22, 24));

        JLabel title = new JLabel("주문 내역");
        title.setFont(HEADING_FONT);
        title.setForeground(TEXT);
        title.setBorder(BorderFactory.createEmptyBorder(0, 0, 16, 0));
        orderArea.add(title, BorderLayout.NORTH);

        orderItemsPanel.setOpaque(false);
        orderItemsPanel.setLayout(new BoxLayout(orderItemsPanel, BoxLayout.Y_AXIS));
        JScrollPane scrollPane = new JScrollPane(orderItemsPanel);
        scrollPane.setBorder(null);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.getVerticalScrollBar().setUnitIncrement(12);
        orderArea.add(scrollPane, BorderLayout.CENTER);

        JPanel footer = new JPanel();
        footer.setOpaque(false);
        footer.setLayout(new BoxLayout(footer, BoxLayout.Y_AXIS));

        JPanel totalRow = new JPanel(new BorderLayout());
        totalRow.setOpaque(false);
        totalRow.setBorder(BorderFactory.createEmptyBorder(18, 0, 16, 0));
        JLabel totalTitle = new JLabel("총 금액");
        totalTitle.setFont(BODY_FONT);
        totalTitle.setForeground(SECONDARY_TEXT);
        totalPriceLabel.setFont(new Font("Apple SD Gothic Neo", Font.BOLD, 21));
        totalPriceLabel.setForeground(TEXT);
        totalRow.add(totalTitle, BorderLayout.WEST);
        totalRow.add(totalPriceLabel, BorderLayout.EAST);

        JButton clearButton = createButton("주문 비우기", new Color(235, 233, 228), TEXT);
        clearButton.addActionListener(event -> clearOrder());

        footer.add(totalRow);
        footer.add(clearButton);
        orderArea.add(footer, BorderLayout.SOUTH);
        return orderArea;
    }

    private JButton createButton(String text, Color background, Color foreground) {
        JButton button = new JButton(text);
        button.setFont(new Font("Apple SD Gothic Neo", Font.BOLD, 13));
        button.setForeground(foreground);
        button.setBackground(background);
        button.setOpaque(true);
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        button.setMaximumSize(new Dimension(Integer.MAX_VALUE, 42));
        button.setPreferredSize(new Dimension(120, 42));
        return button;
    }

    private void addMenu(int menuIndex) {
        if (menus[menuIndex].hasStock(quantities[menuIndex] + 1)) {
            quantities[menuIndex]++;
            updateOrderSummary();
        }
    }

    private void clearOrder() {
        for (int i = 0; i < quantities.length; i++) {
            quantities[i] = 0;
        }
        updateOrderSummary();
    }

    private void updateOrderSummary() {
        orderItemsPanel.removeAll();
        int totalPrice = 0;
        boolean hasOrder = false;

        for (int i = 0; i < menus.length; i++) {
            if (quantities[i] > 0) {
                hasOrder = true;
                int itemPrice = menus[i].getPrice() * quantities[i];
                totalPrice += itemPrice;
                orderItemsPanel.add(createOrderRow(menus[i], quantities[i], itemPrice));
                orderItemsPanel.add(Box.createVerticalStrut(12));
            }
        }

        if (!hasOrder) {
            JLabel emptyLabel = new JLabel("아직 담은 메뉴가 없습니다.");
            emptyLabel.setFont(BODY_FONT);
            emptyLabel.setForeground(SECONDARY_TEXT);
            emptyLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
            orderItemsPanel.add(emptyLabel);
        }

        totalPriceLabel.setText(formatMoney(totalPrice));
        orderItemsPanel.revalidate();
        orderItemsPanel.repaint();
    }

    private JPanel createOrderRow(Menu menu, int quantity, int itemPrice) {
        JPanel row = new JPanel(new BorderLayout(10, 0));
        row.setOpaque(false);
        row.setMaximumSize(new Dimension(Integer.MAX_VALUE, 54));

        JPanel copy = new JPanel();
        copy.setOpaque(false);
        copy.setLayout(new BoxLayout(copy, BoxLayout.Y_AXIS));
        JLabel name = new JLabel(menu.getName());
        name.setFont(new Font("Apple SD Gothic Neo", Font.BOLD, 14));
        name.setForeground(TEXT);
        JLabel count = new JLabel(quantity + "개");
        count.setFont(BODY_FONT);
        count.setForeground(SECONDARY_TEXT);
        copy.add(name);
        copy.add(count);

        JLabel price = new JLabel(formatMoney(itemPrice));
        price.setFont(BODY_FONT);
        price.setForeground(TEXT);
        row.add(copy, BorderLayout.WEST);
        row.add(price, BorderLayout.EAST);
        return row;
    }

    private String formatMoney(int value) {
        return NumberFormat.getIntegerInstance().format(value) + "원";
    }

    @SuppressWarnings("serial")
    private static class RoundedPanel extends JPanel {
        private final int radius;
        private final Color fillColor;

        RoundedPanel(int radius, Color fillColor) {
            this.radius = radius;
            this.fillColor = fillColor;
            setOpaque(false);
        }

        @Override
        protected void paintComponent(Graphics graphics) {
            Graphics2D graphics2D = (Graphics2D) graphics.create();
            graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON);
            graphics2D.setColor(fillColor);
            graphics2D.fillRoundRect(0, 0, getWidth(), getHeight(), radius, radius);
            graphics2D.dispose();
            super.paintComponent(graphics);
        }
    }
}
