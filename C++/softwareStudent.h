
#ifndef SOFTWARESTUDENT_H
#define SOFTWARESTUDENT_H

#include <iostream>
#include <string>
#include "student.h"


#ifdef __cplusplus
extern "C" {
#endif

class SoftwareStudent : public Student {
public:

    SoftwareStudent();

    ~SoftwareStudent();

    SoftwareStudent(string studentId, string firstName, string lastName, string emailAddress, int age, int* numDays);

};

#ifdef __cplusplus
}
#endif

#endif /* SOFTWARESTUDENT_H */

