module org.analyze.main {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires com.almasb.fxgl.all;
    requires tablesaw.core;
    requires java.desktop;
    requires javafx.swing;
    requires org.apache.poi.ooxml;
    requires org.apache.logging.log4j;
    requires javafx.media;
    requires  io.github.classgraph;
    requires atlantafx.base;
    requires  mysql.connector.j;
    requires org.apache.poi.poi;
    requires org.apache.poi.ooxml.schemas;
    requires  org.apache.logging.log4j.core;
    requires java.sql;

    opens org.analyze.main to javafx.fxml;
    exports org.analyze.main;
    exports Controller;
    opens Controller to javafx.fxml;
    exports Table_Formation;
    opens Table_Formation to javafx.fxml;
    exports Controller.chart_operations;
    opens Controller.chart_operations to javafx.fxml;
}