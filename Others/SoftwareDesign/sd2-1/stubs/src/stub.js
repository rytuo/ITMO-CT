const express = require('express');

module.exports = function run(port) {
    const app = express();
    app.get('/search', (req, res) => {
        const query = req.query['q'];
        if (!query || (typeof query != "string")) {
            throw new Error("No query parameter provided");
        }
        res.send({
            documents: [
                {title: `Got 1 ${query} from ${port}`},
                {title: `Got 2 ${query} from ${port}`},
                {title: `Got 3 ${query} from ${port}`},
                {title: `Got 4 ${query} from ${port}`},
                {title: `Got 5 ${query} from ${port}`},
            ],
        });
    });
    app.listen(port, () => console.log('Running on ' + port));
}
