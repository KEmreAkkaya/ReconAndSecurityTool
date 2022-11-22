package com.mycompany.project.FXController;

import com.mycompany.project.IPScanner.IPScanner;
import com.mycompany.project.PortScanner.PortScanner;
import com.mycompany.project.Interfaces.IPScannerInterface;
import com.mycompany.project.Interfaces.PortScannerInterface;
import com.mycompany.project.Validation.IPValidation;
import com.mycompany.project.Validation.PortValidation;

import java.io.IOException;

import java.net.URL;

import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;

import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class MainController implements Initializable, IPScannerInterface, PortScannerInterface {

    ExecutorService executorService = null;

    @FXML
    private VBox MainVBox;

    //Main Buttons
    @FXML
    private Button home_button;
    @FXML
    private Button scanner_button;
    @FXML
    private Button windowsh_button;
    @FXML
    private Button linuxh_button;

    @FXML
    private Button penetrationtesting_button;
    @FXML
    private Button stressTesting_button;
    @FXML
    private Button aboutusButton;
    @FXML
    private Button reportbutton;
    @FXML
    private Button vulnerabilityScannerButton;
    @FXML
    private Button exit_button;

    //Panes
    @FXML
    private StackPane StackPane;
    @FXML
    private AnchorPane vulnerabilityPane;
    @FXML
    private AnchorPane homePane;
    @FXML
    private AnchorPane scannerPane;
    @FXML
    private AnchorPane portscannerPane;

    //PortPane's elements
    @FXML
    private Button portscanner_button;
    @FXML
    private TextField portscanner_ipaddress_TextField;
    @FXML
    private ChoiceBox<String> portscanner_scannerType_choicebox;
    @FXML
    private Button portscanner_startScanner_button;
    @FXML
    private Button portscanner_stopScanner_button;
    @FXML
    private ChoiceBox<String> portscanner_mask_choicebox;
    @FXML
    private TextField portscanner_portnumberFirst_TextField;
    @FXML
    private TextField portscanner_portnumberSecond_TextField;
    @FXML
    private TextArea portscanner_output_textArea;

    //IPScanner's elements
    @FXML
    private TextField ipscanner_ipaddress_TextField;
    @FXML
    private ChoiceBox<String> ipscanner_scannerType_choicebox;
    @FXML
    private Button ipscanner_startScanner_button;
    @FXML
    private Button ipscanner_stopScanner_button;
    @FXML
    private TextArea ipscanner_output_textArea;
    @FXML
    private ChoiceBox<String> ipscanner_mask_choicebox;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        StackPane.getChildren().clear();
        StackPane.getChildren().add(homePane);

    }

    @FXML
    private void homeButtonOnAction(ActionEvent event) {

        StackPane.getChildren().clear();
        StackPane.getChildren().add(homePane);

    }

    @FXML
    private void scannerButtonOnAction(ActionEvent event) {

        StackPane.getChildren().clear();

        //Adding the choices to the maskchoicebox
        ipscanner_mask_choicebox.getItems().addAll("8", "16", "24", "32");
        ipscanner_mask_choicebox.getSelectionModel().select(2);

        ipscanner_scannerType_choicebox.getItems().addAll("ICMP Scan", "TCP SYN Scan", "TCP ACK Scan", "UDP Ping");
        ipscanner_scannerType_choicebox.getSelectionModel().select(0);
        StackPane.getChildren().add(scannerPane);

    }

    @FXML
    private void portscannerButtonOnAction(ActionEvent event) {

        StackPane.getChildren().clear();

        //Adding the choices to the maskchoicebox
        portscanner_mask_choicebox.getItems().addAll("8", "16", "24", "32");
        portscanner_mask_choicebox.getSelectionModel().select(2);

        portscanner_scannerType_choicebox.getItems().addAll("TCP ", "UDP");
        portscanner_scannerType_choicebox.getSelectionModel().select(0);

        StackPane.getChildren().add(portscannerPane);

    }

    private void vulnerabilityButtonOnAction(ActionEvent event) {

        StackPane.getChildren().clear();
        StackPane.getChildren().add(homePane);
    }

    private void penetrationButtonOnAction(ActionEvent event) {

        StackPane.getChildren().clear();
        StackPane.getChildren().add(vulnerabilityPane);
    }

    @FXML
    private void windowshButtonOnAction(ActionEvent event) {

        StackPane.getChildren().clear();
        StackPane.getChildren().add(vulnerabilityPane);
    }

    @FXML
    private void linuxhButtonOnAction(ActionEvent event) {

        StackPane.getChildren().clear();
        StackPane.getChildren().add(vulnerabilityPane);
    }

    @FXML
    private void penetrationtestingButtonOnAction(ActionEvent event) {
        StackPane.getChildren().clear();
        StackPane.getChildren().add(vulnerabilityPane);
    }

    @FXML
    private void stresstestingButtonOnAction(ActionEvent event) {
        StackPane.getChildren().clear();
        StackPane.getChildren().add(vulnerabilityPane);
    }

    @FXML
    private void aboutusButtonOnAction(ActionEvent event) {
        StackPane.getChildren().clear();
        StackPane.getChildren().add(vulnerabilityPane);
    }

    @FXML
    private void reportbuttonButtonOnAction(ActionEvent event) {
        StackPane.getChildren().clear();
        StackPane.getChildren().add(vulnerabilityPane);
    }

    @FXML
    private void vulnerabilityscannerButtonOnAction(ActionEvent event) {
        StackPane.getChildren().clear();
        StackPane.getChildren().add(vulnerabilityPane);
    }

    @FXML
    private void ipscanner_startScannerButtonOnAction(ActionEvent event) {

        if (executorService == null) {
            executorService = Executors.newFixedThreadPool(10);
        }

        executorService.submit(new Runnable() {
            @Override
            public void run() {

                String ip = ipscanner_ipaddress_TextField.getText();
                String cidrMask = (String) ipscanner_mask_choicebox.getValue();

                if (!IPValidation.validate(ip)) {
                    ipscanner_output_textArea.appendText("IP Address is not valid. \n");
                    return;
                }

                int cidrMasktoInt = Integer.parseInt(ipscanner_mask_choicebox.getValue());

                ipscanner_output_textArea.appendText("IP Scanner is starting. \n");
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException ex) {
                    Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
                }

                ipscanner_output_textArea.appendText("Please just wait a few minutes ... \n");

                getIPScanner(ip, cidrMasktoInt);

                List<String> ipList = IPScanner.getIpList();

                if (!ipList.isEmpty() && ipList.size() != 0) {

                    for (int i = 0; i < ipList.size(); i++) {
                        ipscanner_output_textArea.appendText("> IP: " + ipList.get(i) + " is reachable");
                        ipscanner_output_textArea.appendText("\n");
                    }

                }

                ipscanner_output_textArea.appendText("Finished successfully.\n");

            }

        });

    }

    @FXML
    private void ipscanner_stopScannerButtonOnAction(ActionEvent event) throws IOException, InterruptedException {

        if (executorService == null) {
            ipscanner_output_textArea.appendText("No running threads.\n");
        } else {
            executorService.shutdownNow();
            executorService = null;
        }
    }

    @FXML
    private void portscanner_startScannerButtonOnAction(ActionEvent event) {

        if (executorService == null) {
            executorService = Executors.newFixedThreadPool(10);
        }

        executorService.submit(new Runnable() {
            @Override
            public void run() {
                String ipaddress = portscanner_ipaddress_TextField.getText();
                String sp = portscanner_portnumberFirst_TextField.getText();
                String ep = portscanner_portnumberSecond_TextField.getText();

                if (!IPValidation.validate(ipaddress)) {
                    portscanner_output_textArea.appendText("IP Address is not valid. \n");
                    return;
                }

                if (!PortValidation.validate(sp)) {
                    portscanner_output_textArea.appendText("First Port Value is not valid. \n");
                    return;
                }
                if (!PortValidation.validate(ep)) {
                    portscanner_output_textArea.appendText("End Port Value is not valid. \n");
                    return;
                }

                int startport = Integer.parseInt(sp);
                int endport = Integer.parseInt(ep);

                portscanner_output_textArea.appendText("Port Scanner is starting. \n");
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException ex) {
                    Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
                }

                portscanner_output_textArea.appendText("Please just wait a few minutes to scan ... \n");

                getPortScanner(ipaddress, startport, endport);

                List<String> portList = PortScanner.getportList();

                if (!portList.isEmpty() && portList.size() != 0) {

                    for (int i = 0; i < portList.size(); i++) {
                        portscanner_output_textArea.appendText(portList.get(i));
                        portscanner_output_textArea.appendText("\n");

                    }
                }
                portscanner_output_textArea.appendText("Finished successfully.\n");

            }

        });

    }

    @FXML
    private void portscanner_stopScannerButtonOnAction(ActionEvent event) {

        if (executorService == null) {
            portscanner_output_textArea.appendText("No running threads.\n");
        } else {
            executorService.shutdownNow();
            executorService = null;

        }

    }

    @Override
    public ConcurrentSkipListSet<String> getIPScanner(String ipaddress, int mask) {
        ConcurrentSkipListSet<String> ipsSet = new ConcurrentSkipListSet();
        IPScanner.scan(ipaddress, mask);

        return ipsSet;
    }

    @FXML
    private void exitButtonOnAction(ActionEvent event) {

        System.exit(0);
    }

    @Override
    public void getPortScanner(String ip, int startport, int endport) {
        try {
            PortScanner.TCPPortScanner(ip, startport, endport);
        } catch (ExecutionException | InterruptedException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
