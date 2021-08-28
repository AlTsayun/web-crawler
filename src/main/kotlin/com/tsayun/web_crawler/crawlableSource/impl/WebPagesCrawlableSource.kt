package com.tsayun.web_crawler.crawlableSource.impl

import com.tsayun.web_crawler.crawlableSource.CrawlableSource
import com.tsayun.web_crawler.crawler.Crawlable
import java.net.URI
import java.net.URL

class WebPagesCrawlableSource(val url: URL, val linkDepth: Int, val pagesLimit: Int) : CrawlableSource {

    override fun iterator(): Iterator<Crawlable> {
        var a = object: AbstractIterator<Crawlable>() {
            override fun computeNext() {

            }
        }
        return object: AbstractIterator<Crawlable>() {
            override fun computeNext() {

            }
        }
    }
}