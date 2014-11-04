(function ($, _) {
   "use strict";

   var idRegex = /<(\w*)>/g;
   var htmlUttrykk = {};

   function finnIder(uttrykk) {
      var id;
      var matches = [];
      while (id = idRegex.exec(uttrykk)) {
         matches.push(id);
      }
      return matches;
   }

   function finnDisplayNavn(uttrykk) {
      var navn;

      if (!_.isUndefined(uttrykk["navn"])) {
         navn = uttrykk["navn"];
      } else if (!_.isUndefined(uttrykk["verdi"])) {
         navn = uttrykk["verdi"];
      } else {
         navn = uttrykk["uttrykk"];
      }

      return navn;
   }

   function lagHtmlForUttrykk(uttrykkId, alleUttrykk) {
      if(_.isUndefined(htmlUttrykk[uttrykkId])) {
         var uttrykk = alleUttrykk[uttrykkId];
         var beregning = uttrykk["uttrykk"];

         var ider = finnIder(beregning);
         var subUttrykk = [];

         _.forEach(ider, function (id) {
            var uttrykkForId = alleUttrykk[id[1]];
            var displayNavn;

            if (!_.isUndefined(uttrykkForId["navn"])) {
               displayNavn = "<b>" + uttrykkForId["navn"] + "</b>";
               subUttrykk.push([displayNavn, uttrykkForId]);
               lagHtmlForUttrykk(id[1], alleUttrykk);
            } else {
               displayNavn = finnDisplayNavn(uttrykkForId);
            }

            beregning = beregning.replace(id[0], displayNavn);
         });

         var html =
            "<p>" + finnDisplayNavn(uttrykk) + " = " + uttrykk["verdi"] + "</p>" +
            "<hr/>" +
            "<p>Beregning: " + beregning + "</p>";

         _.forEach(subUttrykk, function (elem) {
            html += "<p>" + elem[0] + " = " + elem[1]["verdi"] + "</p>";
         });

         htmlUttrykk[uttrykkId] = html;
      } else {
         html = htmlUttrykk[uttrykkId];
      }

      return html;
   }

   $.getJSON("/api/uttrykk/beregneOgBeskrive", {}, function (data) {
      $("#eksempel").html(lagHtmlForUttrykk(data["startId"], data["uttrykk"]));
      console.log(htmlUttrykk);
   });
}(jQuery, _));