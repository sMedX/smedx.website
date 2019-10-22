package app.web.GraphQL;

import graphql.schema.Coercing;
import graphql.schema.GraphQLScalarType;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Locale;

/**
 * Created by anton_arakcheyev on 06/12/2018.
 */
@Component("gqlScalarType")
public class Scalars {

    protected static Logger logger = Logger.getLogger(Scalars.class.getName());


    public static GraphQLScalarType DateTime = new GraphQLScalarType("DateTime", "DataTime scalar", new Coercing() {

        @Override
        public String serialize(Object input) {
            //serialize the ZonedDateTime into string on the way out
            //return ((ZonedDateTime) input).format(DateTimeFormatter.ISO_OFFSET_DATE_TIME);
            //SimpleDateFormat simpleDateFormat = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss");
            //SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd MMM yyyy");
            try {
                //return simpleDateFormat.format(Date.from(((Timestamp) input).toInstant()));
                /*Date dt = Date.from(((Timestamp) input).toInstant());
                return DateTimeFormatter.ofLocalizedDate(FormatStyle.LONG)
                        .withLocale(new Locale("ru"))
                        .format(LocalDate.of(dt.getYear() + 1900, dt.getMonth(), dt.getDay()));*/
                Timestamp ts = (Timestamp) input;
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss");
                //System.out.println(simpleDateFormat.format(ts.getDate()));
                //System.out.println(ts.getYear()+"-"+ts.getMonth()+"-"+ts.getDay());
                //System.out.println(ts.toString());


                return DateTimeFormatter.ofLocalizedDate(FormatStyle.LONG)
                        .withLocale(new Locale("ru"))
                        .format(ts.toLocalDateTime());
            } catch (Exception ex) {
                logger.error(ex.getMessage());
                ex.printStackTrace();
                return "";
            }
        }

        @Override
        public Object parseValue(Object input) {
            return serialize(input);
        }

        @Override
        public ZonedDateTime parseLiteral(Object input) {
            //parse the string values coming in
            if (input instanceof String) {
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss");
                try {
                    ZonedDateTime createdAt = ZonedDateTime.ofInstant(simpleDateFormat.parse((String) input).toInstant(),
                            ZoneId.of("UTC"));
                    return createdAt;
                } catch (ParseException e) {
                    e.printStackTrace();
                    logger.error(e.getMessage());
                    return null;
                }
            } else {
                logger.error("Uknown format of date: " + input.getClass());
                return null;
            }

            /*if (input instanceof StringValue) {
                return ZonedDateTime.parse(((StringValue) input).getValue());
            } else {
                return null;
            }*/
        }
    });
}
