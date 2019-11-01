import json, requests, sys, os.path
import rest_conf as conf
import indicators_rest as indicators
import unittest


class IndicatorTest(unittest.TestCase):

    def test_indicators(self):
        ind = conf.INDICATOR_ADD
        resp = indicators.add_indicator(ind)
        self.assertEqual(resp.status_code, 200, "Invalid response code {0}, {1}".format(resp.status_code, resp.content))

        found = indicators.find_indicator(ind)
        self.assertTrue(found, "Added indicator not found")

        resp_del = indicators.delete_indicator(ind)
        self.assertEqual(resp_del.status_code, 200, "Invalid response delete code {0}, {1}".format(resp_del.status_code, resp_del.content))

        found = indicators.find_indicator(ind)
        self.assertTrue(not found, "Indicator was not deleted")





if __name__ == "__main__":
    unittest.main()

