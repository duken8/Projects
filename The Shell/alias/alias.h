
#ifndef ALIAS_H
#define ALIAS_H

#include <stdio.h>
#include <stdlib.h>
#include <string.h>

struct alias
{
    char * name;
    char * command;
};
typedef struct alias alias;

void clearAlias(void * passedIn);
void printAlias(void * passedIn);
void * buildAlias(char *alias);

#endif
