package view;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import viewmodel.ViewState;

/**
 * JavaFX controller class for the clientOfferView view.
 * @author Group8-SEP2
 * @version 1.0.0 2021
 */


public class ClientOfferViewController extends ViewController{
    @FXML private Label offerTitle,title, description, address,type, numberOfRooms,pricePerMonth,deposit,area,floor,landlordName,error,alertMessage;
    @FXML private TextArea messageToLandlord;
    @FXML private TextField password;
    @FXML private Button request,accept,refuse,message;

    /**
     * Overridden method from the ViewController abstract class that initializes the controller after its root element has been completely processed
     */
    @Override
    protected void init() {
        getViewModelFactory().getOfferViewViewModel().setInterface();
        this.offerTitle.textProperty().bind(getViewModelFactory().getOfferViewViewModel().getTitle());
        this.title.textProperty().bind(getViewModelFactory().getOfferViewViewModel().getTitle());
        this.description.textProperty().bind(getViewModelFactory().getOfferViewViewModel().getDescription());
        this.address.textProperty().bind(getViewModelFactory().getOfferViewViewModel().getAddress());
        this.type.textProperty().bind(getViewModelFactory().getOfferViewViewModel().getType());
        this.numberOfRooms.textProperty().bind(getViewModelFactory().getOfferViewViewModel().getNumberOfRooms());
        this.pricePerMonth.textProperty().bind(getViewModelFactory().getOfferViewViewModel().getPricePerMonth());
        this.deposit.textProperty().bind(getViewModelFactory().getOfferViewViewModel().getDeposit());
        this.area.textProperty().bind(getViewModelFactory().getOfferViewViewModel().getArea());
        this.floor.textProperty().bind(getViewModelFactory().getOfferViewViewModel().getFloor());
        this.landlordName.textProperty().bind(getViewModelFactory().getOfferViewViewModel().getLandlordName());
        this.password.textProperty().bindBidirectional(getViewModelFactory().getOfferViewViewModel().getPassword());
        this.error.textProperty().bindBidirectional(getViewModelFactory().getOfferViewViewModel().getError());
        this.messageToLandlord.textProperty().bindBidirectional(getViewModelFactory().getOfferViewViewModel().getMessageToLandlord());
        reset();
    }

    /**
     * Method executed everytime the view and the controller are set.
     */
    public void reset(){
        if(ViewState.getInstance().getOffer()!=null) {
            this.error.setText("");
            if ((ViewState.getInstance().getOffer().getUsernameOfOfferer().equals("") || ViewState.getInstance().getOffer().getUsernameOfOfferer() == null) && ViewState.getInstance().getOffer().getLandlord().getUsername().equals(ViewState.getInstance().getUser().getUsername())) {
                alertMessage.setText("No requests available at the moment!");
                this.accept.setVisible(false);
                this.refuse.setVisible(false);
                this.password.setVisible(false);
                this.request.setVisible(false);
                this.messageToLandlord.setVisible(false);
                this.message.setVisible(false);
            } else if ((ViewState.getInstance().getOffer().getUsernameOfOfferer().equals("") || ViewState.getInstance().getOffer().getUsernameOfOfferer() == null) && !ViewState.getInstance().getOffer().getLandlord().getUsername().equals(ViewState.getInstance().getUser().getUsername())) {
                alertMessage.setText("*this action will be unreversible, therefore you need to insert your password to be able to send a request to the landlord*");
                this.accept.setVisible(false);
                this.refuse.setVisible(false);
                this.request.setVisible(true);
                this.password.setVisible(true);
                this.messageToLandlord.setVisible(true);
                this.message.setVisible(true);
            } else if ((!ViewState.getInstance().getOffer().getUsernameOfOfferer().equals("") || ViewState.getInstance().getOffer().getUsernameOfOfferer() != null) && ViewState.getInstance().getOffer().getLandlord().getUsername().equals(ViewState.getInstance().getUser().getUsername())) {
                this.error.setText("You just received an offer from "+ViewState.getInstance().getOffer().getUsernameOfOfferer());
                alertMessage.setText("*this action will be unreversible, therefore you need to insert your password to be able to send a request to the landlord*");
                this.accept.setVisible(true);
                this.refuse.setVisible(true);
                this.request.setVisible(false);
                this.password.setVisible(true);
                this.messageToLandlord.setVisible(false);
                this.message.setVisible(false);
            } else {
                alertMessage.setText("There is already a request in pending.");
                this.accept.setVisible(false);
                this.refuse.setVisible(false);
                this.request.setVisible(false);
                this.password.setVisible(false);
                this.messageToLandlord.setVisible(true);
                this.message.setVisible(true);
            }
        }
    }

    /**
     * This method changes the view and displays the HomePage
     */
    @FXML public void onBack(){
        getViewHandler().openView("homePage");
    }

    /**
     * This method sends a message to the landlord delegating the ViewModel of the controller.
     */
    @FXML public void sendMessage(){
        getViewModelFactory().getOfferViewViewModel().sendMessage();
        this.messageToLandlord.setText("");
    }

    /**
     * This method sends a request to the landlord delegating the ViewModel of the controller.
     */
    @FXML public void sendRequest(){
        getViewModelFactory().getOfferViewViewModel().sendRequest();
        if(error.getText().equals(""))
        getViewHandler().openView("offersList");
    }

    /**
     * This method accepts a request from a tenant for a property delegating the ViewModel of the controller.
     */
    @FXML public void acceptRequest(){
        getViewModelFactory().getOfferViewViewModel().acceptRequest();
        if(error.getText().equals(""))
        getViewHandler().openView("homePage");
    }

    /**
     * This method refuses a request from a tenant for a property delegating the ViewModel of the controller.
     */
    @FXML public void refuseRequest(){
        getViewModelFactory().getOfferViewViewModel().refuseRequest();
        if(error.getText().equals(""))
        getViewHandler().openView("offersList");
    }


}
