/* doesn't work with source level 1.8:
module noone.mymediaplayer {
    requires javafx.controls;
    requires javafx.fxml;

    opens noone.mymediaplayer to javafx.fxml;
    exports noone.mymediaplayer;
}
*/
