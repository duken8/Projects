#include "process.h"

void forkIt(char ** argv)
{
	int status;

	pid_t pid = fork();
	
	if(pid < 0)
	{
		printf("Error forking child\n");
		exit(-99);
	}		
	else if(pid != 0)//parent
	{
		while(waitpid(pid, &status, 0) != pid);
	}
	else//child
	{
	       if(execvp(argv[0], argv) < 0)
	       {
	       	   printf("Error: Invalid command\n");
	       	   exit(-99);
	       }	   
	}
			
}//end forkIt

void buildRc(FILE ** msshrc, char *** alias, int * histCount, int * histFileCount, int * aliasCount, int * pathCount)
{
	char temp[MAX], c;
	int count = 0;
  	while((c = getc(*msshrc)) != '=');
  	fscanf(*msshrc, "%d", histCount);
  	fflush(*msshrc);//flush buffer
  	while((c = getc(*msshrc)) != '=');
  	fscanf(*msshrc, "%d", histFileCount);
  	fflush(*msshrc);//flush
  	
  	//alias stuff
  	fgets(temp, MAX, *msshrc);//clear \n
  	fgets(temp, MAX, *msshrc);//read empty line
  	while(fgets(temp, MAX, *msshrc) != NULL)
  	{
  		count++;
  	}//count=#ofalias	
  	rewind(*msshrc);
  	int x;
  	for(x = 0; x < 3; x++)
  		fgets(temp, MAX, *msshrc);
  	//at the first alias
  	*alias = (char **) calloc(count, sizeof(char*));
  	for(x = 0; x < count; x++)
  	{
  		fgets(temp, MAX, *msshrc);
  		strip(temp);
  		*alias[x] = (char *)calloc(strlen(temp)-1, sizeof(char));
  		*alias[x] = temp;
  	}
  	*aliasCount = count;//sets # of alias for cleanup
  	//path stuff
}//end buildRc
