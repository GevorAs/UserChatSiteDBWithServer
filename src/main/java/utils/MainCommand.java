package utils;

public interface MainCommand {

    int EXIT = 0;
    int LOGOUT = 0;

    int LOGIN = 1;
    int REGISTER = 2;


    int SEND_MAIL = 1;
    int PRINT_MY_MESSAGES = 4;
      int UPDATE_USER_DATA=3;
      int NEW_MESSAGES=2;
      int SEND_REQUEST=5;
      int SEE_REQUESTS=6;
      int SEE_FRIENDS_AND_EDIT=7;
      int UPDATE_NAME=1;
      int UPDATE_SURNAME=2;
      int UPDATE_PASSWORD=3;
}
