#ifndef NETWORKSTUDENT_H
#define NETWORKSTUDENT_H

#include <iostream>
#include <string>
#include "student.h"
#include "degree.h"

#ifdef __cplusplus
extern "C" {
#endif

class NetworkStudent : public Student {

public:
    NetworkStudent();

    ~NetworkStudent();

    NetworkStudent(string &studentId, string &firstName, string &lastName, string &emailAddress, int &age,
                   int* numDays);
};

#ifdef __cplusplus
}
#endif

#endif /* NETWORKSTUDENT_H */

