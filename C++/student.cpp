#include <iostream>
#include <string>
#include "student.h"
#include "degree.h"
#include "roster.h"

using namespace std;

Student::Student() {};

// the constructor
Student::Student(string &studentId, string &firstName, string &lastName, string &emailAddress, int &age, int numDays[3],
                 Degree degree) {
    this->studentId = studentId;
    this->firstName = firstName;
    this->lastName = lastName;
    this-> emailAddress = emailAddress;
    this->age = age;
    SetNumDays(numDays);
    this->degree = degree;
}

// the destructor
Student::~Student() {}


void Student::print() {
    string degreeString;
    if (degree == NETWORKING) {
        degreeString = "NETWORKING";
    } else if (degree == SECURITY) {
        degreeString = "SECURITY";
    } else {
        degreeString = "SOFTWARE";
    }

    cout << studentId;
    cout << "\tFirst Name: " << firstName;
    cout << "\tLast Name: " << lastName;
    cout << "\tEmail Address: " << emailAddress;
    cout << "\tAge: " << age;
    cout << "\tDaysInCourse: " << numDays[0] << "," << numDays[1] << "," << numDays[2];
    cout << "\tDegree Program: " << degreeString;
    cout << endl;
}

// the setters
void Student::SetStudentId(string &studentId) {
    this->studentId = studentId;
    return;
}

void Student::SetFirstName(string &firstName) {
    this->firstName = firstName;
    return;
}

void Student::SetLastName(string &lastName) {
    this->lastName = lastName;
    return;
}

void Student::SetEmailAddress(string &emailAddress) {
    this->emailAddress = emailAddress;
    return;
}

void Student::SetAge(int &age) {
    this->age = age;
    return;
}

void Student::SetNumDays(int *numDays) {
    for (int i = 0; i < 3; i++) {
        this->numDays[i] = numDays[i];
    };
    return;
}

void Student::SetDegree(Degree degree) {
    this->degree = degree;
    return;
}

//Getters
string &Student::GetStudentId() {

    return studentId;
}

string &Student::GetFirstName() {

    return firstName;
}

string &Student::GetLastName() {

    return lastName;
}

string &Student::GetEmailAddress() {

    return emailAddress;
}

int &Student::GetAge() {

    return age;
}

int *Student::GetNumDays() {

    return numDays;
}

Degree Student::GetDegree() {

    return degree;
}
