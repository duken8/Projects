//Duke, Nick -- Data Object that represents all fields within the table
namespace FormProject
{
    class EventInfo
    {
        private string extension, fileName, path, eventType, dateTime;
        public EventInfo(string extension, string fileName, string path, string eventType, string dateTime)
        {
            this.extension = extension;
            this.fileName = fileName;
            this.path = path;
            this.eventType = eventType;
            this.dateTime = dateTime;
        }

        public string Extension
        {
            get
            {
                return extension;
            }
        }

        public string FileName
        {
            get
            {
                return fileName;
            }
        }

        public string Path
        {
            get
            {
                return path;
            }
        }

        public string EventType
        {
            get
            {
                return eventType;
            }
        }

        public string DateTime
        {
            get
            {
                return dateTime;
            }
        }
    }
}
