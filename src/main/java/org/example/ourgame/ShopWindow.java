package org.example.ourgame;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.List;

public class ShopWindow extends Stage {

    private GameController gameController;

    /**
     * Konstruktor, mis loob ShopWindow akna.
     * @param gameController - viide mängukontrollerile
     */
    public ShopWindow(GameController gameController) {
        this.gameController = gameController;
        this.initModality(Modality.APPLICATION_MODAL);
        this.setTitle("Klounide Pood");
        VBox clownsBox = setupClownsDisplay();

        Scene scene = new Scene(clownsBox);
        this.setScene(scene);
    }

    /**
     * Meetod, mis loob ja tagastab VBox'i, mis sisaldab kättesaadavaid kloune.
     * @return VBox - kasti, mis sisaldab klounide kirjeid
     */
    private VBox setupClownsDisplay() {
        VBox clownsBox = new VBox(10);
        clownsBox.setAlignment(Pos.CENTER);
        clownsBox.setPadding(new Insets(20));

        // Värskendame klounide nimekirja enne akna kuvamist
        List<ClownsClass> availableClowns = gameController.getAvailableClowns();
        for (ClownsClass clown : availableClowns) {
            HBox clownEntry = createClownEntry(clown);
            clownsBox.getChildren().add(clownEntry);
        }

        return clownsBox;
    }

    /**
     * Meetod, mis loob ja tagastab HBox'i, mis esindab üksikut klouni koos pildi, taseme ja hinnaga.
     * @param clown - klouni klassi objekt
     * @return HBox - klouni kirje kast
     */
    private HBox createClownEntry(ClownsClass clown) {
        ImageView clownImage = new ImageView(new Image(clown.getPicture(), 100, 100, true, true));
        double price = Math.pow(clown.getClownLevel(), 3) + 5;
        Label label = new Label(clown.getName() + ", Tase " + clown.getClownLevel() + " - Hind: " + price);
        Button buyButton = new Button("Osta");
        buyButton.setOnAction(e -> {
            try {
                gameController.buyClown(clown.getClownLevel());
                close(); // Sulgeme akna pärast ostu sooritamist
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        HBox clownEntry = new HBox(10, clownImage, label, buyButton);
        clownEntry.setAlignment(Pos.CENTER);
        return clownEntry;
    }
}
