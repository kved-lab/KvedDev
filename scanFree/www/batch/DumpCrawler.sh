rm dumpCrawler.data
mysqldump -h localhost.localDomain -u webuser -v -pwebuser -r/var/www/html/crawl/dumpCrawler.data crawler url 
gzip -f dumpCrawler.data
