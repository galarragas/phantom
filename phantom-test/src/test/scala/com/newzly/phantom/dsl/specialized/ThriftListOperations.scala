package com.newzly.phantom.dsl.specialized

import org.scalatest.concurrent.PatienceConfiguration
import org.scalatest.time.SpanSugar._
import com.newzly.phantom.Implicits._
import com.newzly.phantom.helper.{Sampler, BaseTest}
import com.newzly.phantom.tables.ThriftColumnTable
import com.newzly.phantom.thrift.Implicits._
import com.newzly.phantom.thrift.ThriftTest
import com.newzly.util.finagle.AsyncAssertionsHelper._

class ThriftListOperations extends BaseTest {
  val keySpace = "thriftlistoperations"

  implicit val s: PatienceConfiguration.Timeout = timeout(10 seconds)

  it should "prepend an item to a thrift list column" in {
    ThriftColumnTable.insertSchema

    val sample = ThriftTest(
      Sampler.getARandomInteger(),
      Sampler.getAUniqueString,
      test = true
    )

    val sample2 = ThriftTest(
      Sampler.getARandomInteger(),
      Sampler.getAUniqueString,
      test = true
    )

    val insert = ThriftColumnTable.insert
      .value(_.id, sample.id)
      .value(_.name, sample.name)
      .value(_.ref, sample)
      .value(_.thriftSet, Set(sample))
      .value(_.thriftList, List(sample))
      .future()

    val operation = for {
      insertDone <- insert
      update <- ThriftColumnTable.update.where(_.id eqs sample.id).modify(_.thriftList prepend sample2).future()
      select <- ThriftColumnTable.select(_.thriftList).where(_.id eqs sample.id).one
    } yield {
      select
    }

    operation.successful {
      items => {
        items.isDefined shouldBe true
        items.get shouldBe List(sample2, sample)
      }
    }
  }

  it should "prepend several items to a thrift list column" in {
    ThriftColumnTable.insertSchema

    val sample = ThriftTest(
      Sampler.getARandomInteger(),
      Sampler.getAUniqueString,
      test = true
    )

    val sample2 = ThriftTest(
      Sampler.getARandomInteger(),
      Sampler.getAUniqueString,
      test = true
    )

    val sample3 = ThriftTest(
      Sampler.getARandomInteger(),
      Sampler.getAUniqueString,
      test = true
    )

    val toAppend = List(sample2, sample3)

    val insert = ThriftColumnTable.insert
      .value(_.id, sample.id)
      .value(_.name, sample.name)
      .value(_.ref, sample)
      .value(_.thriftSet, Set(sample))
      .value(_.thriftList, List(sample))
      .future()

    val operation = for {
      insertDone <- insert
      update <- ThriftColumnTable.update.where(_.id eqs sample.id).modify(_.thriftList prependAll toAppend).future()
      select <- ThriftColumnTable.select(_.thriftList).where(_.id eqs sample.id).one
    } yield {
      select
    }

    operation.successful {
      items => {
        items.isDefined shouldBe true
        items.get shouldBe List(sample3, sample2, sample)
      }
    }
  }

  it should "append an item to a thrift list column" in {
    ThriftColumnTable.insertSchema

    val sample = ThriftTest(
      Sampler.getARandomInteger(),
      Sampler.getAUniqueString,
      test = true
    )

    val sample2 = ThriftTest(
      Sampler.getARandomInteger(),
      Sampler.getAUniqueString,
      test = true
    )

    val insert = ThriftColumnTable.insert
      .value(_.id, sample.id)
      .value(_.name, sample.name)
      .value(_.ref, sample)
      .value(_.thriftSet, Set(sample))
      .value(_.thriftList, List(sample))
      .future()

    val operation = for {
      insertDone <- insert
      update <- ThriftColumnTable.update.where(_.id eqs sample.id).modify(_.thriftList append sample2).future()
      select <- ThriftColumnTable.select(_.thriftList).where(_.id eqs sample.id).one
    } yield {
      select
    }

    operation.successful {
      items => {
        items.isDefined shouldBe true
        items.get shouldBe List(sample, sample2)
      }
    }
  }

  it should "append several items to a thrift list column" in {
    ThriftColumnTable.insertSchema

    val sample = ThriftTest(
      Sampler.getARandomInteger(),
      Sampler.getAUniqueString,
      test = true
    )

    val sample2 = ThriftTest(
      Sampler.getARandomInteger(),
      Sampler.getAUniqueString,
      test = true
    )

    val sample3 = ThriftTest(
      Sampler.getARandomInteger(),
      Sampler.getAUniqueString,
      test = true
    )

    val toAppend = List(sample2, sample3)

    val insert = ThriftColumnTable.insert
      .value(_.id, sample.id)
      .value(_.name, sample.name)
      .value(_.ref, sample)
      .value(_.thriftSet, Set(sample))
      .value(_.thriftList, List(sample))
      .future()

    val operation = for {
      insertDone <- insert
      update <- ThriftColumnTable.update.where(_.id eqs sample.id).modify(_.thriftList appendAll toAppend).future()
      select <- ThriftColumnTable.select(_.thriftList).where(_.id eqs sample.id).one
    } yield {
      select
    }

    operation.successful {
      items => {
        items.isDefined shouldBe true
        items.get shouldBe List(sample, sample2, sample3)
      }
    }
  }

  it should "remove an item from a thrift list column" in {
    ThriftColumnTable.insertSchema

    val sample = ThriftTest(
      Sampler.getARandomInteger(),
      Sampler.getAUniqueString,
      test = true
    )

    val sample2 = ThriftTest(
      Sampler.getARandomInteger(),
      Sampler.getAUniqueString,
      test = true
    )

    val insert = ThriftColumnTable.insert
      .value(_.id, sample.id)
      .value(_.name, sample.name)
      .value(_.ref, sample)
      .value(_.thriftSet, Set(sample))
      .value(_.thriftList, List(sample, sample2))
      .future()

    val operation = for {
      insertDone <- insert
      update <- ThriftColumnTable.update.where(_.id eqs sample.id).modify(_.thriftList remove sample2).future()
      select <- ThriftColumnTable.select(_.thriftList).where(_.id eqs sample.id).one
    } yield {
      select
    }

    operation.successful {
      items => {
        items.isDefined shouldBe true
        items.get shouldBe List(sample)
      }
    }
  }

  it should "remove several items from a thrift list column" in {
    ThriftColumnTable.insertSchema

    val sample = ThriftTest(
      Sampler.getARandomInteger(),
      Sampler.getAUniqueString,
      test = true
    )

    val sample2 = ThriftTest(
      Sampler.getARandomInteger(),
      Sampler.getAUniqueString,
      test = true
    )

    val sample3 = ThriftTest(
      Sampler.getARandomInteger(),
      Sampler.getAUniqueString,
      test = true
    )

    val insert = ThriftColumnTable.insert
      .value(_.id, sample.id)
      .value(_.name, sample.name)
      .value(_.ref, sample)
      .value(_.thriftSet, Set(sample))
      .value(_.thriftList, List(sample, sample2, sample3))
      .future()

    val operation = for {
      insertDone <- insert
      update <- ThriftColumnTable.update.where(_.id eqs sample.id).modify(_.thriftList removeAll List(sample2, sample3)).future()
      select <- ThriftColumnTable.select(_.thriftList).where(_.id eqs sample.id).one
    } yield {
      select
    }

    operation.successful {
      items => {
        items.isDefined shouldBe true
        items.get shouldBe List(sample)
      }
    }
  }
}
