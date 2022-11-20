package com.btrobot.btrobot;

public class NumberTo3Digit {
    String number = "";
    NumberTo3Digit(String _number){
        int _numberLength = _number.length();

        if (_numberLength == 3)
            number = _number;
        else if (_numberLength == 2)
            number = "0" + _number;
        else if (_numberLength == 1)
            number = "00" + _number;
    }
}
