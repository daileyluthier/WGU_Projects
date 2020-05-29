#include <iostream>
#include <string>
#include <sstream>
#include "roster.h"
#include "degree.h"
#include "student.h"

//define functions

Roster::Roster(){}

bool Roster::add(string studentId, string firstName, string lastName, string emailAddress, int age, int numDays1,
                 int numDays2, int numDays3, Degree degree) {

    Student *tempStudent;
    int numDays[3] = {numDays1, numDays2, numDays3};
    switch (degree) {
        case SECURITY:
            tempStudent = new SecurityStudent(studentId, firstName, lastName, emailAddress, age, numDays);
            break;
        case NETWORKING:
            tempStudent = new NetworkStudent(studentId, firstName, lastName, emailAddress, age, numDays);
            break;
        case SOFTWARE:
            tempStudent = new SoftwareStudent(studentId, firstName, lastName, emailAddress, age, numDays);
            break;
    }

    bool added;
    for (int i = 0; i < MAX; i++) {

        if (classRoster[i] == nullptr) {

            classRoster[i] = tempStudent;
            added = true;
            break;
        }
    }
    return added;
}

void Roster::remove(string studentId) {
    bool removed = false;
    for (int i = 0; i < MAX; i++) {

        if (classRoster[i] != nullptr && classRoster[i]->GetStudentId() == studentId) {
            classRoster[i] = nullptr;
            removed = true;
        }
    }
}

void Roster::printAll() {

    for (int i = 0; i < MAX; i++) {

        if (classRoster[i] != nullptr) {

            classRoster[i]->print();

        }
    }
}

void Roster::printInvalidEmails() {

    string emailAddress;
    for (int i = 0; i < MAX; i++) {

        if (classRoster[i] == nullptr) {
            continue;
        }
        emailAddress = classRoster[i]->GetEmailAddress();

        if (emailAddress.find("@") == string::npos || emailAddress.find(".") == string::npos ||
                emailAddress.find(" ") != string::npos) {

            cout << "Invalid email Address: " << emailAddress << "\n";
        }
    }
}

void Roster::printByDegreeProgram(Degree degree) {

    cout << "Students by Software degree program: " << endl;
    for (int i = 0; i < MAX; i++) {

        if (classRoster[i] != nullptr && (classRoster[i]->GetDegree() == SOFTWARE)) {

            classRoster[i]->print();
        }
    }
}

void Roster::printAverageDaysInCourse(string studentId) {
    int sum = 0;
    double average;

    for (int i = 0; i < MAX; i++) {
        if (classRoster[i] != nullptr && (classRoster[i]->GetStudentId() == studentId)) {

            int *nDays = classRoster[i]->GetNumDays();

            for (int b = 0; b < 3; b++) {

                sum += nDays[b];
            }
            average = sum / 3;
            cout << "Student ID: " << studentId;
            cout << "\tAverage Days In Course: " << average << endl;
        }
    }
}

using namespace std;

int main() {
    Roster classRoster;

    cout << "\tCourse Title: C867" << "  C++" << "\t#000862648  Michael Dailey" << endl;

    string input, token;
    string classRosterArray[9];
    Degree degree;
    for (int i = 0; i < MAX; i++) {
        input = studentData[i];
        istringstream sd(input);

        int z = 0;
        while (getline(sd, token, ',')) {
            classRosterArray[z] = token;
            z = z + 1;
        }

        if (classRosterArray[8] == "SECURITY") {

            degree = SECURITY;

        } else if (classRosterArray[8] == "NETWORK") {

            degree = NETWORKING;

        } else {

            degree = SOFTWARE;

        }

        classRoster.add(classRosterArray[0], classRosterArray[1], classRosterArray[2], classRosterArray[3],
                        stoi(classRosterArray[4]), stoi(classRosterArray[5]), stoi(classRosterArray[6]),
                        stoi(classRosterArray[7]), degree);

    }

    classRoster.printAll();
    cout << endl;

    classRoster.printInvalidEmails();
    cout << endl;

    //print via current object's student ID
    for (int i = 0; i < MAX; i++) {
        classRoster.printAverageDaysInCourse(classRoster.classRoster[i]->GetStudentId());
        cout << endl;
      }
    
    classRoster.printByDegreeProgram(SOFTWARE);
    cout << endl;

    classRoster.remove("A3");
    
    classRoster.remove("A3");

    return 0;
}



         
         
         
         