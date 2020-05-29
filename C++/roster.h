#ifndef ROSTER_H
#define ROSTER_H

#include <iostream>
#include <string>
#include "securityStudent.h"
#include "softwareStudent.h"
#include "networkStudent.h"
#include "student.h"

#ifdef __cplusplus
extern "C" {
#endif

const string studentData[] =
 {"A1,John,Smith,John1989@gm ail.com,20,30,35,40,SECURITY",
 "A2,Suzan,Erickson,Erickson_1990@gmailcom,19,50,30,40,NETWORK",
 "A3,Jack,Napoli,The_lawyer99yahoo.com,19,20,40,33,SOFTWARE",
 "A4,Erin,Black,Erin.black@comcast.net,22,50,58,40,SECURITY",
 "A5,Michael,Dailey,mdail16@wgu.edu,30,30,25,60,SOFTWARE"};

const int MAX = 5;

    class Roster {
public:
    Roster();
    bool add(string studentId, string firstName, string lastName, string emailAddress, int age, int numdays1, int numdays2, int numdays3, Degree degree);
    void remove(string studentId);
    void printAll();
    void printInvalidEmails();
    void printByDegreeProgram(Degree degree);
    void printAverageDaysInCourse(string studentId);
    Student *classRoster[MAX] = {nullptr, nullptr, nullptr, nullptr, nullptr};
};

#ifdef __cplusplus
}
#endif

#endif /* ROSTER_H */

