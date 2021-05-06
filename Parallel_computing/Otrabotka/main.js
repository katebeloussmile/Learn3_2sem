const fs = require('fs')
const parseString = require('xml2js').parseString

function OBJtoXML(obj) {
    var xml = '';
    for (var prop in obj) {
        //заполнение тегов ключами json документа
        xml += "<" + prop + ">";
        if (typeof obj[prop] == "object") {
            //рекурсивный вызов для вложенных тегов
            xml += OBJtoXML(new Object(obj[prop]));
        } else {
            //добавление значения на индексе ключа
            xml += obj[prop];
        }
        //закрывающий тег ключа
        xml += "</" + prop + ">";
    }
    return xml
}

//read json document
fs.readFile('./toparse.json', 'utf8', (err, jsonString) => {
    //throw error if can't read fil
    if (err) {
        console.log("Error reading file:", err)
        return
    }
    //parse json
    try {
        //https://developer.mozilla.org/en-US/docs/Web/JavaScript/Reference/Global_Objects/JSON/parse
        console.log("JSON parse result:")
        const parser = JSON.parse(jsonString)
        //parse object window from json
        console.log(parser.widget.window)
        //convert window to xml
        console.log("JSON to XML result:")
        console.log(OBJtoXML(parser.widget.window))
    }
    //catch error
    catch(err) {
        console.log('Error parsing JSON string:', err)
    }
})

fs.readFile('./toparse.xml', 'utf8', (err, xmlString) => {
    parseString(xmlString, function (err, result) {
        console.log("XML parse result")
        console.dir(result.widget.window);
    });
})


