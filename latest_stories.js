const express = require('express')
const axios = require('axios')
const cheerio = require('cheerio')

const app = express();

const TIME_URL = 'https://time.com';


app.get('/getTimeStories', async function (req, res) {
    let result = [];
    
    let time_response = await axios.get(TIME_URL, {
        headers: {
            'Accept-Encoding': 'application/json',
        }
    });

    const $ = cheerio.load(time_response.data);

    $('.latest-stories__item')
    .each((index, element) => {

        const story_data = {};

        const title= $(element).find('.latest-stories__item-headline').text();

        const link = $(element).find('a').attr('href');

        story_data['title'] = title;
        story_data['link'] = TIME_URL + link;

        result.push(story_data);
    });

    console.log(result)

    res.send(JSON.stringify(result))
 })
 

app.listen(3000, () => {
    console.log(`Server Started at ${3000}`)
})
