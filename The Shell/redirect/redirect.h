#ifndef REDIRECT_H
#define REDIRECT_H

#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#include <unistd.h>
#include <unistd.h>
#include <sys/wait.h>
#include <sys/types.h>


int containsOut(char * s);
int containsIn(char * s);

#endif
