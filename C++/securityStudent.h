
#ifndef SECURITYSTUDENT_H
#define SECURITYSTUDENT_H

#include <iostream>
#include <string>
#include "student.h"

#ifdef __cplusplus
extern "C" {
#endif


class SecurityStudent : public Student {

public:
    SecurityStudent();
    ~SecurityStudent();
    SecurityStudent(string studentId, string firstName, string lastName, string emailAddress, int age, int* numDays);
};


#ifdef __cplusplus
}
#endif
#endif /* SECURITYSTUDENT_H */