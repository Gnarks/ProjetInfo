module code.projetinfo {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires com.almasb.fxgl.all;
    requires junit;
    requires com.fasterxml.jackson.core;
    requires com.fasterxml.jackson.databind;
    requires javafx.media;

    opens code.projetinfo to javafx.fxml;
    exports code.projetinfo;
    opens code.projetinfo.controllertests to javafx.fxml;
    exports code.projetinfo.controllertests;
}