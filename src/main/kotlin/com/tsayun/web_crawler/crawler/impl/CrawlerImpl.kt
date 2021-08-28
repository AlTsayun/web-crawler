package com.tsayun.web_crawler.crawler.impl

import com.tsayun.web_crawler.crawler.*
import java.util.function.Consumer
import java.util.function.Function

class CrawlerImpl : Crawler {
    override fun crawl(
        source: Iterator<Crawlable>,
        parser: Function<Crawlable, CrawlableParsed>,
        consumer: Consumer<CrawlableParsed>
    ) {
        //todo: implement multithreading crawl
        while(source.hasNext()){
            consumer.accept(parser.apply(source.next()));
        }
    }

}