#include <iostream>
#include <string>
#include "securityStudent.h"
#include "roster.h"
#include "degree.h"

SecurityStudent::SecurityStudent() {};

SecurityStudent::SecurityStudent(string studentId, string firstName, string lastName, string emailAddress, int age, int* numDays) {
    this->studentId = studentId;
    this->firstName = firstName;
    this->lastName = lastName;
    this->emailAddress = emailAddress;
    this->age = age;
    this->numDays[0] = numDays[0];
    this->numDays[1] = numDays[1];
    this->numDays[2] = numDays[2];
    this->degree = SECURITY;
}