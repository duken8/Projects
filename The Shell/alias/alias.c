#include "alias.h"

void clearAlias(void * passedIn)
{
	alias *a = (alias *)passedIn;
	free(a->name);
	free(a->command);
}

void printAlias(void * passedIn)
{
	alias *a = (alias *)passedIn;
	printf("alias %s='%s'\n", a->name, a->command);
}//end printHistory

void * buildAlias(char *alias)
{
	/*alias *a = (alias *) calloc(1, sizeof(alias));
	int x, index;
	char *token = NULL;
	for(x = 0; x < strlen(alias)-1; x++)
	{
		if(alias[x] == '=')
			index = x;	
	}//index = # where = is
	token = strtok(alias, "=") */
	return NULL;
	
}//end buildAlias
