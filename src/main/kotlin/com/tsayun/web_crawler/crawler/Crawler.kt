package com.tsayun.web_crawler.crawler

import java.util.function.Consumer
import java.util.function.Function

interface Crawler {
    fun crawl(
        source: Iterator<Crawlable>,
        mapper: Function<Crawlable, CrawlableParsed>,
        consumer: Consumer<CrawlableParsed>
    )
}