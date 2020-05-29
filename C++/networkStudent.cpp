#include <iostream>
#include <string>
#include "student.h"
#include "networkStudent.h"
#include "degree.h"

using namespace std;

NetworkStudent::NetworkStudent() {}


NetworkStudent::NetworkStudent(string &studentId, string &firstName, string &lastName, string &emailAddress, int &age,
                               int* numDays) {
    this->studentId = studentId;
    this->firstName = firstName;
    this->lastName = lastName;
    this->emailAddress = emailAddress;
    this->age = age;
    this->numDays[0] = numDays[0];
    this->numDays[1] = numDays[1];
    this->numDays[2] = numDays[2];
    this->degree = NETWORKING;
}