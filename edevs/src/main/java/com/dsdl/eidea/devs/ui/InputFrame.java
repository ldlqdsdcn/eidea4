/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dsdl.eidea.devs.ui;


import com.dsdl.eidea.devs.model.GenModelDto;
import com.dsdl.eidea.devs.service.CodeGenerationService;
import com.dsdl.eidea.util.DateTimeHelper;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.File;
import java.util.*;
import java.util.List;

/**
 *
 * @author admin
 */
public class InputFrame extends JFrame {
    private static ResourceBundle rb;
    private static ApplicationContext applicationContext;
    private List<GenModelDto> genModelDtoList=new ArrayList<>();
    private Map<String,GenModelDto> childrenMap=new HashMap<>();
    private Map<String,File> outModuleMap=new HashMap<>();
    private String[] tableHeaders;
    private String[] values=null;
    /**
     * Creates new form InputFrame
     */
    public InputFrame() {
        initComponents();
        initLabel();
        init();

    }
    private void updateChildModuleList(String text)
    {
        List<String> childItems=new ArrayList<>();
        for(GenModelDto model:genModelDtoList)
        {
            if(!model.getTableName().equals(text))
            {
                childrenMap.put(model.getTableName(), model);
                childItems.add(model.getTableName());
            }
        }
        values=new String[childItems.size()];
        childItems.toArray(values);
        includeModuleList.setModel(new AbstractListModel<String>() {
            String[] strings = values;
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
    }
    private void initLabel()
    {
        tableHeaders=new String [] {
                rb.getString("dev.generation.tool.tablename"),  rb.getString("dev.generation.tool.classname"), rb.getString("dev.generation.tool.modulename"),
                rb.getString("dev.generation.tool.name"), rb.getString("dev.generation.tool.gen_service"), rb.getString("dev.generation.tool.generate_action"),
                rb.getString("dev.generation.tool.include_module"), rb.getString("dev.generation.tool.output_location"), rb.getString("dev.generation.tool.is_translation"), rb.getString("dev.generation.tool.is_physical_paging")
        };
        tableLabel.setText(rb.getString("dev.generation.tool.tablename"));
        nameLabel.setText(rb.getString("dev.generation.tool.name"));
        moduleLabel.setText(rb.getString("dev.generation.tool.modulename"));
        classNameLabel.setText(rb.getString("dev.generation.tool.classname"));
        genServiceLabel.setText(rb.getString("dev.generation.tool.gen_service"));
        genActionLabel.setText(rb.getString("dev.generation.tool.generate_action"));
        outputLocationLabel.setText(rb.getString("dev.generation.tool.output_location"));
        includeModule.setText(rb.getString("dev.generation.tool.include_module"));
        transLabel.setText(rb.getString("dev.generation.tool.is_translation"));
        pagingLabel.setText(rb.getString("dev.generation.tool.is_physical_paging"));
        addButton.setText(rb.getString("dev.generation.tool.add"));
        deleteButton.setText(rb.getString("dev.generation.tool.remove_selected_row"));
        generateButton.setText(rb.getString("dev.generation.tool.generate_code"));
        settingMenu.setText(rb.getString("dev.generation.tool.setting"));

        zhCnCheckBoxMenuItem.setText(rb.getString("dev.generation.tool.zh_cn"));
        enUsCheckBoxMenuItem.setText(rb.getString("dev.generation.tool.en_us"));
    }
    private void init()
    {
        if(rb.getLocale().toString().equals("zh_CN"))
        {
            zhCnCheckBoxMenuItem.setSelected(true);
            enUsCheckBoxMenuItem.setSelected(false);
        }
        else
        {
            zhCnCheckBoxMenuItem.setSelected(false);
            enUsCheckBoxMenuItem.setSelected(true);
        }

        this.setTitle(rb.getString("dev.generation.tool.title"));
        // Listen for changes in the text
        this.tableText.getDocument().addDocumentListener(new DocumentListener() {
            public void changedUpdate(DocumentEvent e) {
                updateChildModuleList(tableText.getText());
            }
            public void removeUpdate(DocumentEvent e) {
                updateChildModuleList(tableText.getText());
            }
            public void insertUpdate(DocumentEvent e) {
                updateChildModuleList(tableText.getText());
            }

        });
        genServiceCheck.setSelected(true);
        genActionCheck.setSelected(true);

        includeModuleList.setModel(new AbstractListModel<String>() {
            String[] strings = {  };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        outputLocationComboBox.removeAllItems();

        File directory = new File("");
        msgTextArea.append(rb.getString("dev.generation.tool.current.path"));
        msgTextArea.append(directory.getAbsolutePath()+"\n");
        File currentFolder=new File(directory.getAbsolutePath());
        File[] files= currentFolder.listFiles();
        if(files!=null)
            for(File f:files)
            {
                if(f.isDirectory())
                {
                    String name=f.getName().substring( directory.getName().length());
                    if(!name.contains(".")&&!name.contains("web")&&!name.contains("dev")&&!name.contains("doc")&&!name.contains("util"))
                    {outputLocationComboBox.addItem(name);
                        outModuleMap.put(name, f);
                    }
                }

            }
    }
    private void updateForm(String table_name)
    {
        GenModelDto genModel=null;
        for(GenModelDto model:genModelDtoList)
        {
            if(model.getTableName().equals(table_name))
            {
                genModel=model;
                break;
            }
        }
        assert genModel != null;
        tableText.setText(genModel.getTableName());
        classNameText.setText(genModel.getModelName());
        moduleText.setText(genModel.getModule());
        nameText.setText(genModel.getRemark());
        genServiceCheck.setSelected(genModel.isGenerateService());
        genActionCheck.setSelected(genModel.isGenerateWeb());
        outputLocationComboBox.setSelectedItem(genModel.getOutputModule());
        transCheck.setSelected(genModel.isTrl());
        pagingCheck.setSelected(genModel.isPagingByDb());
        List<GenModelDto> includeModelList= genModel.getIncludeModelList();
        if(includeModelList!=null)
        {
            for(GenModelDto genModelDto:includeModelList)
            {
                this.includeModuleList.setSelectedValue(genModelDto.getTableName(), rootPaneCheckingEnabled);
            }
        }



    }
    private void updateTable()
    {
        modelTable.removeAll();
        int i=0;
        Object[][] content=  new Object [genModelDtoList.size()][10];

        for(GenModelDto genModelDto:genModelDtoList)
        {
            content[i][0]=genModelDto.getTableName();
            content[i][1]=genModelDto.getModelName();
            content[i][2]=genModelDto.getModule();
            content[i][3]=genModelDto.getRemark();
            content[i][4]=genModelDto.isGenerateService()?rb.getString("dev.generation.tool.yes"):rb.getString("dev.generation.tool.no");
            content[i][5]=genModelDto.isGenerateWeb()?rb.getString("dev.generation.tool.yes"):rb.getString("dev.generation.tool.no");
            String lineName="";
            if(genModelDto.getIncludeModelList()!=null)
                for(GenModelDto line:genModelDto.getIncludeModelList())
                {
                    lineName+=" "+line.getTableName();
                }

            content[i][6]=lineName;
            content[i][7]=genModelDto.getOutputModule();
            content[i][8]=genModelDto.isTrl()?rb.getString("dev.generation.tool.yes"):rb.getString("dev.generation.tool.no");
            content[i][9]=genModelDto.isPagingByDb()?rb.getString("dev.generation.tool.yes"):rb.getString("dev.generation.tool.no");
            i++;
        }
        DefaultTableModel defaultTableModel=  new DefaultTableModel(
                content,tableHeaders
        ) {
            boolean[] canEdit = new boolean [] {
                    false, false, false, false, false, false, false, false, false,false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        };

        this.modelTable.setModel(defaultTableModel);
    }


    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">
    private void initComponents() {

        inputPanel = new JPanel();
        tableLabel = new JLabel();
        nameLabel = new JLabel();
        tableText = new JTextField();
        nameText = new JTextField();
        moduleLabel = new JLabel();
        classNameText = new JTextField();
        classNameLabel = new JLabel();
        moduleText = new JTextField();
        genServiceLabel = new JLabel();
        genServiceCheck = new JCheckBox();
        genActionLabel = new JLabel();
        genActionCheck = new JCheckBox();
        outputLocationLabel = new JLabel();
        outputLocationComboBox = new JComboBox<>();
        includeModule = new JLabel();
        pagingCheck = new JCheckBox();
        transLabel = new JLabel();
        transCheck = new JCheckBox();
        pagingLabel = new JLabel();
        includeModuleListScrollPane = new JScrollPane();
        includeModuleList = new JList<>();
        buttonPanel = new JPanel();
        addButton = new JButton();
        deleteButton = new JButton();
        generateButton = new JButton();
        modelTableScrollPane = new JScrollPane();
        modelTable = new JTable();
        msgTextAreaScrollPane = new JScrollPane();
        msgTextArea = new JTextArea();
        menuBar = new JMenuBar();
        settingMenu = new JMenu();
        zhCnCheckBoxMenuItem = new JCheckBoxMenuItem();
        enUsCheckBoxMenuItem = new JCheckBoxMenuItem();

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        tableLabel.setBackground(new Color(255, 255, 204));
        tableLabel.setHorizontalAlignment(SwingConstants.RIGHT);


        nameLabel.setHorizontalAlignment(SwingConstants.RIGHT);


        moduleLabel.setBackground(new Color(255, 255, 204));
        moduleLabel.setHorizontalAlignment(SwingConstants.RIGHT);


        classNameLabel.setBackground(new Color(255, 255, 204));
        classNameLabel.setHorizontalAlignment(SwingConstants.RIGHT);


        genServiceLabel.setBackground(new Color(255, 255, 204));
        genServiceLabel.setHorizontalAlignment(SwingConstants.RIGHT);


        genActionLabel.setBackground(new Color(255, 255, 204));
        genActionLabel.setHorizontalAlignment(SwingConstants.RIGHT);


        outputLocationLabel.setHorizontalAlignment(SwingConstants.RIGHT);

        includeModule.setBackground(new Color(255, 255, 204));
        includeModule.setHorizontalAlignment(SwingConstants.RIGHT);

        transLabel.setHorizontalAlignment(SwingConstants.RIGHT);

        pagingLabel.setBackground(new Color(255, 255, 204));
        pagingLabel.setHorizontalAlignment(SwingConstants.RIGHT);

        includeModuleListScrollPane.setViewportView(includeModuleList);

        addButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addButtonActionPerformed(evt);
            }
        });

        deleteButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteButtonActionPerformed(evt);
            }
        });

        generateButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                generateButtonActionPerformed(evt);
            }
        });
        GroupLayout buttonPanelLayout = new GroupLayout(buttonPanel);
        buttonPanel.setLayout(buttonPanelLayout);
        buttonPanelLayout.setHorizontalGroup(
                buttonPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(GroupLayout.Alignment.TRAILING, buttonPanelLayout.createSequentialGroup()
                                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(buttonPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addComponent(generateButton, GroupLayout.Alignment.TRAILING)
                                        .addComponent(deleteButton, GroupLayout.Alignment.TRAILING)
                                        .addComponent(addButton, GroupLayout.Alignment.TRAILING))
                                .addContainerGap())
        );
        buttonPanelLayout.setVerticalGroup(
                buttonPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(buttonPanelLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(addButton)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(deleteButton)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(generateButton)
                                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        GroupLayout inputPanelLayout = new GroupLayout(inputPanel);
        inputPanel.setLayout(inputPanelLayout);
        inputPanelLayout.setHorizontalGroup(
                inputPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(inputPanelLayout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(inputPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addGroup(inputPanelLayout.createSequentialGroup()
                                                .addComponent(tableLabel, GroupLayout.PREFERRED_SIZE, 120, GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(tableText, GroupLayout.PREFERRED_SIZE, 150, GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(classNameLabel, GroupLayout.PREFERRED_SIZE, 120, GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(classNameText, GroupLayout.PREFERRED_SIZE, 150, GroupLayout.PREFERRED_SIZE))
                                        .addGroup(inputPanelLayout.createSequentialGroup()
                                                .addGroup(inputPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                                                        .addGroup(inputPanelLayout.createSequentialGroup()
                                                                .addGroup(inputPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                                                        .addGroup(inputPanelLayout.createSequentialGroup()
                                                                                .addComponent(nameLabel, GroupLayout.PREFERRED_SIZE, 120, GroupLayout.PREFERRED_SIZE)
                                                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                                                .addComponent(nameText, GroupLayout.PREFERRED_SIZE, 150, GroupLayout.PREFERRED_SIZE))
                                                                        .addGroup(inputPanelLayout.createSequentialGroup()
                                                                                .addComponent(outputLocationLabel, GroupLayout.PREFERRED_SIZE, 120, GroupLayout.PREFERRED_SIZE)
                                                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                                                .addComponent(outputLocationComboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
                                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                                .addGroup(inputPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                                                        .addComponent(genServiceLabel, GroupLayout.PREFERRED_SIZE, 120, GroupLayout.PREFERRED_SIZE)
                                                                        .addComponent(pagingLabel, GroupLayout.PREFERRED_SIZE, 120, GroupLayout.PREFERRED_SIZE)))
                                                        .addGroup(inputPanelLayout.createSequentialGroup()
                                                                .addComponent(transLabel, GroupLayout.PREFERRED_SIZE, 120, GroupLayout.PREFERRED_SIZE)
                                                                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                                                .addComponent(transCheck)
                                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                                .addComponent(includeModule, GroupLayout.PREFERRED_SIZE, 120, GroupLayout.PREFERRED_SIZE)))
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                .addGroup(inputPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                                        .addComponent(pagingCheck)
                                                        .addComponent(genServiceCheck)
                                                        .addComponent(includeModuleListScrollPane, GroupLayout.PREFERRED_SIZE, 104, GroupLayout.PREFERRED_SIZE))))
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(inputPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addGroup(inputPanelLayout.createSequentialGroup()
                                                .addGroup(inputPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                                        .addGroup(inputPanelLayout.createSequentialGroup()
                                                                .addComponent(moduleLabel, GroupLayout.PREFERRED_SIZE, 120, GroupLayout.PREFERRED_SIZE)
                                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                                .addComponent(moduleText, GroupLayout.PREFERRED_SIZE, 150, GroupLayout.PREFERRED_SIZE))
                                                        .addGroup(inputPanelLayout.createSequentialGroup()
                                                                .addComponent(genActionLabel, GroupLayout.PREFERRED_SIZE, 120, GroupLayout.PREFERRED_SIZE)
                                                                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                                                .addComponent(genActionCheck)))
                                                .addContainerGap(116, Short.MAX_VALUE))
                                        .addComponent(buttonPanel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );
        inputPanelLayout.setVerticalGroup(
                inputPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(inputPanelLayout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(inputPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(tableLabel)
                                        .addComponent(tableText, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(moduleLabel)
                                        .addComponent(classNameText, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(classNameLabel)
                                        .addComponent(moduleText, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(inputPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addGroup(inputPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                                .addComponent(nameLabel)
                                                .addComponent(nameText, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                                .addComponent(genServiceLabel)
                                                .addComponent(genServiceCheck))
                                        .addComponent(genActionLabel)
                                        .addComponent(genActionCheck))
                                .addGap(18, 18, 18)
                                .addGroup(inputPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                                        .addComponent(pagingLabel)
                                        .addGroup(inputPanelLayout.createSequentialGroup()
                                                .addGroup(inputPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                                        .addGroup(inputPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                                                .addComponent(outputLocationLabel)
                                                                .addComponent(outputLocationComboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                                        .addComponent(pagingCheck))
                                                .addGap(18, 18, 18)
                                                .addGroup(inputPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                                        .addComponent(includeModule)
                                                        .addComponent(transCheck)
                                                        .addComponent(transLabel)
                                                        .addComponent(includeModuleListScrollPane, GroupLayout.PREFERRED_SIZE, 87, GroupLayout.PREFERRED_SIZE)))
                                        .addComponent(buttonPanel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        modelTable.setModel(new DefaultTableModel(
                new Object [][] {},tableHeaders)
        {   boolean[] canEdit = new boolean [] {
                    false, false, false, false, false, false, false, false, false, false
            };
            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        modelTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                modelTableMouseClicked(evt);
            }
        });
        modelTableScrollPane.setViewportView(modelTable);

        msgTextArea.setColumns(20);
        msgTextArea.setRows(5);
        msgTextAreaScrollPane.setViewportView(msgTextArea);

        zhCnCheckBoxMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                zhCnCheckBoxMenuItemActionPerformed(evt);
            }
        });
        settingMenu.add(zhCnCheckBoxMenuItem);

        enUsCheckBoxMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                enUsCheckBoxMenuItemActionPerformed(evt);
            }
        });
        settingMenu.add(enUsCheckBoxMenuItem);

        menuBar.add(settingMenu);

        setJMenuBar(menuBar);

        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(inputPanel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(modelTableScrollPane)
                        .addComponent(msgTextAreaScrollPane)
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addComponent(inputPanel, GroupLayout.PREFERRED_SIZE, 222, GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(msgTextAreaScrollPane, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addGap(20, 20, 20)
                                .addComponent(modelTableScrollPane, GroupLayout.PREFERRED_SIZE, 240, GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>

    private void addButtonActionPerformed(java.awt.event.ActionEvent evt) {
        GenModelDto genModelDto=null;
        boolean include=false;
        for(GenModelDto gen:genModelDtoList)
        {
            if(gen.getTableName().equals(tableText.getText()))
            {
                include=true;
                genModelDto=gen;
                break;
            }
        }
        if(genModelDto==null)
            genModelDto=new GenModelDto();
        genModelDto.setTableName(tableText.getText().trim());
        genModelDto.setModule(moduleText.getText().trim());
        genModelDto.setModelName(classNameText.getText().trim());

        genModelDto.setRemark(nameText.getText().trim());
        genModelDto.setGenerateService(genServiceCheck.isSelected());
        genModelDto.setGenerateWeb(genActionCheck.isSelected());
        genModelDto.setOutputModule(outputLocationComboBox.getSelectedItem().toString());
        genModelDto.setTrl(transCheck.isSelected());
        if(!include)
        {
            genModelDtoList.add(genModelDto);
        }

        List<String> selectedList= includeModuleList.getSelectedValuesList();
        if(selectedList!=null)
        {
            List<GenModelDto> includeModelList= new ArrayList<>();
            for(String childModel:selectedList)
            {
                GenModelDto child=  this.childrenMap.get(childModel);
                includeModelList.add(child);
            }
            genModelDto.setIncludeModelList(includeModelList);
        }
        genModelDto.setPagingByDb(pagingCheck.isSelected());
        updateTable();
    }
    private void deleteButtonActionPerformed(java.awt.event.ActionEvent evt) {
        int selectedRow=modelTable.getSelectedRow();
        DefaultTableModel  model= (DefaultTableModel) modelTable.getModel();
        model.removeRow(selectedRow);
    }
    private void generateButtonActionPerformed(java.awt.event.ActionEvent evt) {
        msgTextArea.append(DateTimeHelper.formatDateTime(new Date())+":"+rb.getString("dev.generation.tool.generating")+"\n");
        if(genModelDtoList.size()>0)
        {
            for(GenModelDto genModelDto:genModelDtoList)
            {
                genModelDto.setOutputPath(outModuleMap.get(genModelDto.getOutputModule()));
            }
            CodeGenerationService codeGenerationService= applicationContext.getBean(CodeGenerationService.class);
            try
            {
                codeGenerationService.generateCode(genModelDtoList);
                msgTextArea.append(DateTimeHelper.formatDateTime(new Date())+":"+rb.getString("dev.generation.tool.generating.success")+"\n");
            }
            catch (Exception e)
            {
                msgTextArea.append(DateTimeHelper.formatDateTime(new Date())+":"+rb.getString("dev.generation.tool.generating.failure")+"\n"+e.getMessage());
            }

        }
    }
    private void modelTableMouseClicked(java.awt.event.MouseEvent evt) {
        DefaultTableModel  model= (DefaultTableModel) modelTable.getModel();
        int selectedRow= modelTable.getSelectedRow();
        String value= model.getValueAt(selectedRow, 0).toString();
        updateForm(value);
    }

    private void enUsCheckBoxMenuItemActionPerformed(java.awt.event.ActionEvent evt) {
        updateLanguage("en_US");
        enUsCheckBoxMenuItem.setSelected(true);
        zhCnCheckBoxMenuItem.setSelected(false);
    }
    private void zhCnCheckBoxMenuItemActionPerformed(java.awt.event.ActionEvent evt) {
        updateLanguage("zh_CN");
        enUsCheckBoxMenuItem.setSelected(false);
        zhCnCheckBoxMenuItem.setSelected(true);
    }
    private void updateLanguage(String language)
    {
        Locale locale=new Locale(language);
        rb=ResourceBundle.getBundle("msg",locale);
        initLabel();
        updateTable();
    }
    public static void main(ResourceBundle resourceBundle) {
        InputFrame.rb=resourceBundle;
        applicationContext=new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
         */
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | UnsupportedLookAndFeelException | IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(InputFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                if(UIManager.getLookAndFeel().isSupportedLookAndFeel()){
                    final String platform = UIManager.getSystemLookAndFeelClassName();
                    // If the current Look & Feel does not match the platform Look & Feel,
                    // change it so it does.
                    if (!UIManager.getLookAndFeel().getName().equals(platform)) {
                        try {
                            UIManager.setLookAndFeel(platform);
                        } catch (Exception exception) {
                            exception.printStackTrace();
                        }
                    }
                }
                InputFrame inputFrame=  new InputFrame();
                int windowWidth = inputFrame.getWidth(); // 获得窗口宽
                int windowHeight = inputFrame.getHeight(); // 获得窗口高
                Toolkit kit = Toolkit.getDefaultToolkit(); // 定义工具包
                Dimension screenSize = kit.getScreenSize(); // 获取屏幕的尺寸
                int screenWidth = screenSize.width; // 获取屏幕的宽
                int screenHeight = screenSize.height; // 获取屏幕的高
                inputFrame.setLocation(screenWidth / 2 - windowWidth / 2, screenHeight / 2 - windowHeight / 2);// 设置窗口居中显示
                inputFrame.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify
    private JButton addButton;
    private JPanel buttonPanel;
    private JLabel classNameLabel;
    private JTextField classNameText;
    private JButton deleteButton;
    private JCheckBoxMenuItem enUsCheckBoxMenuItem;
    private JCheckBox genActionCheck;
    private JLabel genActionLabel;
    private JCheckBox genServiceCheck;
    private JLabel genServiceLabel;
    private JButton generateButton;
    private JLabel includeModule;
    private JList<String> includeModuleList;
    private JScrollPane includeModuleListScrollPane;
    private JPanel inputPanel;
    private JMenuBar menuBar;
    private JTable modelTable;
    private JScrollPane modelTableScrollPane;
    private JLabel moduleLabel;
    private JTextField moduleText;
    private JTextArea msgTextArea;
    private JScrollPane msgTextAreaScrollPane;
    private JLabel nameLabel;
    private JTextField nameText;
    private JComboBox<String> outputLocationComboBox;
    private JLabel outputLocationLabel;
    private JCheckBox pagingCheck;
    private JLabel pagingLabel;
    private JMenu settingMenu;
    private JLabel tableLabel;
    private JTextField tableText;
    private JCheckBox transCheck;
    private JLabel transLabel;
    private JCheckBoxMenuItem zhCnCheckBoxMenuItem;
    // End of variables declaration
}
