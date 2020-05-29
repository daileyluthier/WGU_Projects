#include <iostream>
#include <string>
#include "degree.h"

using namespace std;

#ifndef STUDENT_H
#define STUDENT_H

#ifdef __cplusplus
extern "C" {
#endif

class Student {
public:

//2.c. constructor using all input parameters [change numDay[3]???]
    Student(string &studentId, string &firstName, string &lastName, string &emailAddress, int &age, int numDays[3],
            Degree _degree);

    Student();

    //2.e. destructor
    ~Student();

//2.b. setters
    void SetStudentId(string &studentId);

    void SetFirstName(string &firstName);

    void SetLastName(string &lastName);

    void SetEmailAddress(string &emailAddress);

    void SetAge(int &age);

    void SetNumDays(int *numDays);

    void SetDegree(Degree _degree);

//2.d. virtual print()
    virtual void print();

//2.f. virtual getDegreeProgram()
    void getDegreeProgram();

//2.a. getters
    string &GetStudentId();

    string &GetFirstName();

    string &GetLastName();

    string &GetEmailAddress();

    int &GetAge();

    int *GetNumDays();

    Degree GetDegree();

protected:
    string studentId;
    string firstName;
    string lastName;
    string emailAddress;
    int age;
    int numDays[3];
    Degree degree;
};

#ifdef __cplusplus
}
#endif

#endif /* STUDENT_H */

