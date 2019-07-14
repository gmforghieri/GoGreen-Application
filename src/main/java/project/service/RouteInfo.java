package project.service;

import java.util.List;

/**
 * the purpose of this class to parse JSON response
 * from google Matrix Distance API into RouteInfo object.
 */
public class RouteInfo {

    private String[] destinationAddresses;
    private String[] originAddresses;
    private List<Elements> rows;
    private String status;

    /**
     * This method return integer primitive type of the distance of this route.
     *
     * @return int represent distance of this route in km.
     */
    public int getDistance() {
        Elements result = new Elements(rows.get(0).getElements());
        return result.getElements()[0].getDistance().getValue();
    }

    /**
     * This method return textual representation of the distance of this route.
     *
     * @return String represent distance of this route in km.
     */
    public String getDistanceText() {
        return rows.get(0).getElements()[0].getDistance().getText();
    }

    /**
     * This method return textual representation of the time takes to travel this route.
     *
     * @return String represent travel time in hours and minutes.
     */
    public String getTimeText() {
        return rows.get(0).getElements()[0].getDuration().getText();
    }

    /**
     * all the next private classes is to help parsing JSON response into java class,
     * therefore all the private method and constructor is redundant and just for testing purposes.
     */
    private class Elements {
        private Element[] elements;

        /**
         * Elements object constructor.
         */
        private Elements(Element[] elements) {
            this.elements = elements;
        }

        /**
         * This method return copy of the array of Element object.
         *
         * @return Array of Element object.
         */
        private Element[] getElements() {
            Element[] res = new Element[1];
            res[0] = new Element(elements[0]);
            return res;
        }

        private class Element {
            private TextValue distance;
            private TextValue duration;
            private String status;

            /**
             * Element object constructor.
             * the purpose of this constructor is to get deep-copied TextValue object.
             */
            private Element(Element element) {
                distance = element.getDistance();
                duration = element.getDuration();
            }

            /**
             * getter for distance of the selected Element.
             *
             * @return TextValue object corresponds to distance.
             */
            private TextValue getDistance() {
                TextValue res = new TextValue(distance);
                return distance;
            }

            /**
             * getter for duration of the selected Element.
             *
             * @return TextValue object corresponds to duration.
             */
            private TextValue getDuration() {
                return duration;
            }

            /**
             * TextValue object constructor.
             * the purpose of this constructor is to get deep-copied TextValue object.
             */
            private class TextValue {
                private String text;
                private int value;
                private String status;

                private TextValue(TextValue textValue) {
                    text = textValue.getText();
                    value = textValue.getValue();
                }

                /**
                 * getter for textual representation "String" of this instance.
                 *
                 * @return textual representation of this instance.
                 */
                private String getText() {
                    return text;
                }

                /**
                 * getter for numerical representation "int" of this instance.
                 *
                 * @return numerical representation of this instance.
                 */
                private int getValue() {
                    return value;
                }
            }
        }

    }
}
