package fr.perrot54u.restaurationdb.controllers;

import fr.perrot54u.restaurationdb.views.Views;
import javafx.scene.input.MouseEvent;

public class MenuController {
    public void onSwitchDeterminationListeVehicule(MouseEvent mouseEvent) {
    }

    public void onSwitchReservationVehicule(MouseEvent mouseEvent) {
    }

    public void onSwitchPrixLocation(MouseEvent mouseEvent) {
    }

    public void onSwitchListeAgences(MouseEvent mouseEvent) {
    }

    public void onSwitchListeClients(MouseEvent mouseEvent) {
    }

    public void onDisconnect(MouseEvent mouseEvent) {
        Views.LOGIN_RESTO.loadScene();
    }

}
