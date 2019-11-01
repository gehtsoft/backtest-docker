import json, requests, sys, os.path
import rest_conf as conf
import strategy_rest as strategy
import unittest


class StrategyTest(unittest.TestCase):

    def test_simple_strategy(self):
        self.strategy_check(conf.STRATEGY_ADD)

    def test_pkg_strategy(self):
        self.strategy_check(conf.PKG_ADD)


    def strategy_check(self, name):
        resp = strategy.add_strategy(name)
        self.assertEqual(resp.status_code, 200, "Invalid response code {0}, {1}".format(resp.status_code, resp.content))

        found = strategy.add_strategy(name)
        self.assertTrue(found, "Added strategy not found")

        resp_del = strategy.delete_strategy(name)
        self.assertEqual(resp_del.status_code, 200, "Invalid response delete code {0}, {1}".format(resp_del.status_code, resp_del.content))

        found = strategy.find_strategy(name)
        self.assertTrue(not found, "Strategy was not deleted")





if __name__ == "__main__":
    unittest.main()

